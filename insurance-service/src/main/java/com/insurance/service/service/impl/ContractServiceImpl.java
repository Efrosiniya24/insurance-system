package com.insurance.service.service.impl;

import com.insurance.service.enums.ApplicationStatus;
import com.insurance.service.enums.ContractRegistryStatus;
import com.insurance.service.enums.InsuranceEvent;
import com.insurance.service.exception.BusinessException;
import com.insurance.service.exception.NotFoundException;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryDto;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryResponseDto;
import com.insurance.service.model.application.dto.ApplicationEntityDto;
import com.insurance.service.model.application.dto.ApplicationInsuranceEventDto;
import com.insurance.service.model.contract.dto.ContractBeneficiaryDto;
import com.insurance.service.model.contract.dto.ContractDto;
import com.insurance.service.model.contract.dto.ContractInsuranceEventDto;
import com.insurance.service.model.contract.entity.ContractEntity;
import com.insurance.service.model.contract.mapper.ContractMapper;
import com.insurance.service.model.contract.repository.ContractRepository;
import com.insurance.service.model.sync.entity.ContractRegistrySyncEntity;
import com.insurance.service.model.sync.projection.ContractRegistryStatusProjection;
import com.insurance.service.model.sync.repository.ContractRegistrySyncRepository;
import com.insurance.service.model.user.dto.PersonalDataDto;
import com.insurance.service.security.dto.AuthenticatedUserDto;
import com.insurance.service.security.enums.UserRole;
import com.insurance.service.security.service.AccessService;
import com.insurance.service.service.ApplicationBeneficiaryService;
import com.insurance.service.service.ApplicationInsuranceEventService;
import com.insurance.service.service.ApplicationService;
import com.insurance.service.service.ContractBeneficiaryService;
import com.insurance.service.service.ContractInsuranceEventService;
import com.insurance.service.service.ContractService;
import com.insurance.service.service.UserPersonalDataService;
import com.insurance.service.util.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ApplicationService applicationService;
    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final ApplicationBeneficiaryService applicationBeneficiaryService;
    private final ContractBeneficiaryService contractBeneficiaryService;
    private final ApplicationInsuranceEventService applicationInsuranceEventService;
    private final ContractInsuranceEventService contractInsuranceEventService;
    private final ContractRegistrySyncRepository contractRegistrySyncRepository;
    private final UserPersonalDataService userPersonalDataService;
    private final DocumentUtil documentUtil;
    private final AccessService accessService;

    @Override
    @Transactional
    public ContractDto issueContract(final Long applicationId, final String issuedByUserId) {
        final ApplicationEntityDto application = applicationService.findApplicationByIdForContract(applicationId);

        final Optional<ContractEntity> existingContract = contractRepository.findByApplicationId(applicationId);
        if (existingContract.isPresent()) {
            return toContractDto(existingContract.get(), issuedByUserId, true);
        }

        if (!Objects.equals(application.getApplicationStatus(), ApplicationStatus.APPROVED)) {
            throw new BusinessException("Application isn't approved. Contract can't be issued");
        }

        final LocalDateTime now = LocalDateTime.now();
        final ContractEntity savedContract = contractRepository.save(
            contractMapper.toEntityWhenIssueContract(application, documentUtil.makeDocumentNumber("LC"), issuedByUserId, now)
        );
        final Long contractId = savedContract.getId();

        final List<ApplicationBeneficiaryDto> applicationBeneficiaries =
            applicationBeneficiaryService.getByApplicationId(applicationId);
        contractBeneficiaryService.saveContractBeneficiaryList(
            buildContractBeneficiaryDtoList(applicationBeneficiaries, contractId)
        );

        final List<ApplicationInsuranceEventDto> applicationEvents =
            applicationInsuranceEventService.getByApplicationId(applicationId);
        contractInsuranceEventService.saveContractInsuranceEventList(
            buildContractInsuranceEventDtoList(applicationEvents, contractId)
        );

        contractRegistrySyncRepository.save(
            ContractRegistrySyncEntity.builder()
                .contractId(contractId)
                .contractRegistryStatus(ContractRegistryStatus.PENDING)
                .attemptCount(0)
                .createdAt(now)
                .build()
        );
        applicationService.markAsContractIssued(applicationId, now);
        return toContractDto(savedContract, issuedByUserId, false);
    }

    @Transactional(readOnly = true)
    @Override
    public ContractDto getContract(final Long contractId, final AuthenticatedUserDto currentUser) {
        final ContractEntity contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new NotFoundException("Contract not found"));
        final String applicationUserCreatedId = applicationService.getCreatorUserId(contract.getApplicationId());

        accessService.ownerUserOrUnderwriter(currentUser, applicationUserCreatedId);
        return toContractDto(contract, currentUser.getId(), false);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ContractDto> getContractList(final AuthenticatedUserDto currentUser) {
        final List<ContractEntity> contracts = currentUser.getRoles().contains(UserRole.UNDERWRITER)
            ? contractRepository.findAll()
            : contractRepository.findAllByApplicationIdIn(ownerApplicationIds(currentUser.getId()));
        return toContractDtoList(contracts, currentUser.getId());
    }

    private List<Long> ownerApplicationIds(final String currentUserId) {
        return applicationService.findAllByCreatedUserId(currentUserId);
    }

    private List<ContractBeneficiaryDto> buildContractBeneficiaryDtoList(
        final List<ApplicationBeneficiaryDto> beneficiaries,
        final Long contractId
    ) {
        return beneficiaries.stream()
            .map(beneficiary -> ContractBeneficiaryDto.builder()
                .contractId(contractId)
                .beneficiaryId(beneficiary.getBeneficiaryId())
                .beneficiaryType(beneficiary.getBeneficiaryType())
                .build())
            .toList();
    }

    private List<ContractInsuranceEventDto> buildContractInsuranceEventDtoList(
        final List<ApplicationInsuranceEventDto> insuranceEvents,
        final Long contractId
    ) {
        return insuranceEvents.stream()
            .map(event -> ContractInsuranceEventDto.builder()
                .contractId(contractId)
                .insuranceEvent(event.getInsuranceEvent())
                .build())
            .toList();
    }

    private ContractDto toContractDto(
        final ContractEntity contract,
        final String currentUserId,
        final boolean alreadyIssued
    ) {
        final List<ContractBeneficiaryDto> beneficiaries =
            contractBeneficiaryService.getByContractId(contract.getId());

        final Long policyholderId = contract.getPolicyholderId();
        final Long insuredId = contract.getInsuredId();

        final Map<Long, PersonalDataDto> usersPersonalData = userPersonalDataService.getPersonalDataMapByIds(
            policyholderId,
            insuredId,
            beneficiaries,
            ContractBeneficiaryDto::getBeneficiaryId
        );

        final Set<InsuranceEvent> events = contractInsuranceEventService.getByContractId(contract.getId())
            .stream()
            .map(ContractInsuranceEventDto::getInsuranceEvent)
            .collect(Collectors.toSet());

        final List<ApplicationBeneficiaryResponseDto> beneficiariesDto = beneficiaries.stream()
            .map(beneficiary -> ApplicationBeneficiaryResponseDto.builder()
                .beneficiaryType(beneficiary.getBeneficiaryType())
                .person(usersPersonalData.get(beneficiary.getBeneficiaryId()))
                .build())
            .toList();

        final ContractRegistryStatus registryStatus = contractRegistrySyncRepository.findByContractId(contract.getId())
            .map(ContractRegistrySyncEntity::getContractRegistryStatus)
            .orElse(ContractRegistryStatus.PENDING);

        return contractMapper.toDto(
            contract,
            usersPersonalData.get(policyholderId),
            usersPersonalData.get(insuredId),
            beneficiariesDto,
            events,
            Objects.equals(policyholderId, insuredId),
            registryStatus,
            alreadyIssued,
            alreadyIssued && !Objects.equals(contract.getIssuedByUserId(), currentUserId)
        );
    }

    private List<ContractDto> toContractDtoList(final List<ContractEntity> contracts, final String currentUserId) {
        if (contracts.isEmpty()) {
            return List.of();
        }
        final List<Long> contractIds = contracts.stream()
            .map(ContractEntity::getId)
            .toList();

        final Map<Long, List<ContractBeneficiaryDto>> groupedBeneficiaries =
            contractBeneficiaryService.getByContractIds(contractIds).stream()
                .collect(Collectors.groupingBy(ContractBeneficiaryDto::getContractId));

        final Map<Long, Set<InsuranceEvent>> eventsByContractId =
            contractInsuranceEventService.getByContractIds(contractIds).stream()
                .collect(Collectors.groupingBy(
                    ContractInsuranceEventDto::getContractId,
                    Collectors.mapping(ContractInsuranceEventDto::getInsuranceEvent, Collectors.toSet())
                ));

        final Map<Long, ContractRegistryStatus> registryStatusByContractId =
            contractRegistrySyncRepository.findAllByContractIdIn(contractIds).stream()
                .collect(Collectors.toMap(
                    ContractRegistryStatusProjection::getContractId,
                    ContractRegistryStatusProjection::getContractRegistryStatus
                ));

        final Set<Long> usersPersonalDataId = new HashSet<>();
        contracts.forEach(contract -> {
            usersPersonalDataId.add(contract.getPolicyholderId());
            usersPersonalDataId.add(contract.getInsuredId());
        });
        groupedBeneficiaries.values().forEach(beneficiaries ->
            beneficiaries.forEach(beneficiary -> usersPersonalDataId.add(beneficiary.getBeneficiaryId()))
        );

        final Map<Long, PersonalDataDto> usersPersonalData = userPersonalDataService.getPersonalDataByIds(usersPersonalDataId)
            .stream()
            .collect(Collectors.toMap(PersonalDataDto::getId, Function.identity()));

        return contracts.stream()
            .map(contract -> toContractDto(
                contract,
                currentUserId,
                false,
                usersPersonalData,
                groupedBeneficiaries.get(contract.getId()),
                eventsByContractId.get(contract.getId()),
                registryStatusByContractId.get(contract.getId())
            ))
            .toList();
    }

    private ContractDto toContractDto(
        final ContractEntity contract,
        final String currentUserId,
        final boolean alreadyIssued,
        final Map<Long, PersonalDataDto> usersPersonalData,
        final List<ContractBeneficiaryDto> beneficiaries,
        final Set<InsuranceEvent> events,
        final ContractRegistryStatus registryStatus
    ) {
        final List<ApplicationBeneficiaryResponseDto> beneficiariesDto = beneficiaries.stream()
            .map(beneficiary -> ApplicationBeneficiaryResponseDto.builder()
                .beneficiaryType(beneficiary.getBeneficiaryType())
                .person(usersPersonalData.get(beneficiary.getBeneficiaryId()))
                .build())
            .toList();

        return contractMapper.toDto(
            contract,
            usersPersonalData.get(contract.getPolicyholderId()),
            usersPersonalData.get(contract.getInsuredId()),
            beneficiariesDto,
            events,
            Objects.equals(contract.getPolicyholderId(), contract.getInsuredId()),
            registryStatus,
            alreadyIssued,
            alreadyIssued && !Objects.equals(contract.getIssuedByUserId(), currentUserId)
        );
    }
}
