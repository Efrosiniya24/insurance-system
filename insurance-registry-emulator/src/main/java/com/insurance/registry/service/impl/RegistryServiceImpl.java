package com.insurance.registry.service.impl;

import com.insurance.registry.dto.ContractBeneficiaryDto;
import com.insurance.registry.dto.ContractInsuranceEventDto;
import com.insurance.registry.dto.RegisterContractRequestDto;
import com.insurance.registry.dto.RegisterContractResponseDto;
import com.insurance.registry.dto.RegistryBeneficiaryDto;
import com.insurance.registry.dto.RegistryPersonDataDto;
import com.insurance.registry.entity.RegistryEntity;
import com.insurance.registry.enums.InsuranceEvent;
import com.insurance.registry.mapper.RegistryMapper;
import com.insurance.registry.repository.RegistryRepository;
import com.insurance.registry.service.ContractBeneficiaryService;
import com.insurance.registry.service.ContractInsuranceEventService;
import com.insurance.registry.service.EmulatorModeService;
import com.insurance.registry.service.RegistryService;
import com.insurance.registry.service.RegistryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Service
@RequiredArgsConstructor
public class RegistryServiceImpl implements RegistryService {
    private final EmulatorModeService emulatorModeService;
    private final RegistryUserService registryUserService;
    private final ContractBeneficiaryService contractBeneficiaryService;
    private final ContractInsuranceEventService contractInsuranceEventService;
    private final RegistryRepository registryRepository;
    private final RegistryMapper registryMapper;

    @Override
    @Transactional
    public RegisterContractResponseDto register(final RegisterContractRequestDto request) {
        emulatorModeService.applyMode();
        return registryRepository.findByContractNumber(request.getContractNumber())
            .map(entity -> registryMapper.toResponse(entity, true))
            .orElseGet(() -> registryMapper.toResponse(create(request), false));
    }

    private RegistryEntity create(final RegisterContractRequestDto request) {
        final Long policyholderId = registryUserService.save(request.getPolicyholder());
        final Long insuredUserId = samePerson(request.getPolicyholder(), request.getInsured())
            ? policyholderId
            : registryUserService.save(request.getInsured());
        final RegistryEntity saved = registryRepository.save(
            registryMapper.toEntity(
                request,
                "GR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                LocalDate.now(),
                policyholderId,
                insuredUserId
            )
        );
        final Long registryId = saved.getId();
        contractBeneficiaryService.saveContractBeneficiaryList(
            buildBeneficiaries(request.getBeneficiaries(), registryId, policyholderId, insuredUserId)
        );
        contractInsuranceEventService.saveContractInsuranceEventList(
            buildInsuranceEvents(request.getInsuranceEvents(), registryId)
        );
        return saved;
    }

    private List<ContractBeneficiaryDto> buildBeneficiaries(
        final List<RegistryBeneficiaryDto> beneficiaries,
        final Long registryId,
        final Long policyholderId,
        final Long insuredUserId
    ) {
        if (Objects.isNull(beneficiaries) || beneficiaries.isEmpty()) {
            return List.of();
        }
        final Map<Long, ContractBeneficiaryDto> uniqueByPerson = new LinkedHashMap<>();
        for (final RegistryBeneficiaryDto beneficiary : beneficiaries) {
            final Long beneficiaryId = resolveBeneficiaryId(beneficiary, policyholderId, insuredUserId);
            uniqueByPerson.putIfAbsent(
                beneficiaryId,
                ContractBeneficiaryDto.builder()
                    .registryId(registryId)
                    .beneficiaryId(beneficiaryId)
                    .beneficiaryType(beneficiary.getBeneficiaryType())
                    .build()
            );
        }
        return List.copyOf(uniqueByPerson.values());
    }

    private Long resolveBeneficiaryId(
        final RegistryBeneficiaryDto beneficiary,
        final Long policyholderId,
        final Long insuredUserId
    ) {
        return switch (beneficiary.getBeneficiaryType()) {
            case POLICYHOLDER -> policyholderId;
            case INSURED -> insuredUserId;
            case OTHER -> {
                if (Objects.isNull(beneficiary.getPerson())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty beneficiary data");
                }
                yield registryUserService.save(beneficiary.getPerson());
            }
        };
    }

    private List<ContractInsuranceEventDto> buildInsuranceEvents(
        final Set<InsuranceEvent> insuranceEvents,
        final Long registryId
    ) {
        if (Objects.isNull(insuranceEvents) || insuranceEvents.isEmpty()) {
            return List.of();
        }
        return insuranceEvents.stream()
            .map(event -> ContractInsuranceEventDto.builder()
                .registryId(registryId)
                .insuranceEvent(event)
                .build())
            .toList();
    }

    private boolean samePerson(final RegistryPersonDataDto first, final RegistryPersonDataDto second) {
        return Objects.nonNull(first)
            && Objects.nonNull(second)
            && Objects.equals(first.getIdentificationNumber(), second.getIdentificationNumber());
    }
}
