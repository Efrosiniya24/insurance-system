package com.insurance.service.service.impl;

import com.insurance.service.enums.ApplicationStatus;
import com.insurance.service.enums.InsuranceEvent;
import com.insurance.service.exception.BusinessException;
import com.insurance.service.exception.NotFoundException;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryDto;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryRequestDto;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryResponseDto;
import com.insurance.service.model.application.dto.ApplicationDto;
import com.insurance.service.model.application.dto.ApplicationEntityDto;
import com.insurance.service.model.application.dto.ApplicationFilter;
import com.insurance.service.model.application.dto.ApplicationInsuranceEventDto;
import com.insurance.service.model.application.dto.ApplicationStatusResponseDto;
import com.insurance.service.model.application.dto.ApplicationUpdateStatusDto;
import com.insurance.service.model.application.dto.CreateApplicationRequestDto;
import com.insurance.service.model.application.entity.ApplicationEntity;
import com.insurance.service.model.application.mapper.ApplicationMapper;
import com.insurance.service.model.application.repository.ApplicationRepository;
import com.insurance.service.model.application.specification.ApplicationSpecification;
import com.insurance.service.model.user.dto.PersonalDataDto;
import com.insurance.service.security.dto.AuthenticatedUserDto;
import com.insurance.service.security.enums.UserRole;
import com.insurance.service.security.service.AccessService;
import com.insurance.service.service.ApplicationBeneficiaryService;
import com.insurance.service.service.ApplicationInsuranceEventService;
import com.insurance.service.service.ApplicationService;
import com.insurance.service.service.UserPersonalDataService;
import com.insurance.service.util.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final UserPersonalDataService userPersonalDataService;
    private final ApplicationBeneficiaryService applicationBeneficiaryService;
    private final ApplicationInsuranceEventService applicationInsuranceEventService;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final AccessService accessService;
    private final DocumentUtil documentUtil;

    @Override
    @Transactional(readOnly = true)
    public ApplicationDto getApplication(final Long applicationId, final AuthenticatedUserDto currentUser) {
        final ApplicationEntity application = getApplicationEntity(applicationId);

        accessService.ownerUserOrUnderwriter(currentUser, application.getCreatedByUserId());
        return toApplicationDto(application);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationDto> getApplicationList(
        final AuthenticatedUserDto currentUser,
        final Pageable pageable,
        final ApplicationFilter filter
    ) {
        final Specification<ApplicationEntity> spec = currentUser.getRoles().contains(UserRole.UNDERWRITER)
            ? ApplicationSpecification.from(filter)
            : ApplicationSpecification.createdByUserId(currentUser.getId()).and(ApplicationSpecification.from(filter));
        final Page<ApplicationEntity> applications = applicationRepository.findAll(spec, pageable);
        return new PageImpl<>(toApplicationDtoList(applications.getContent()), pageable, applications.getTotalElements());
    }

    @Override
    @Transactional
    public ApplicationDto createApplication(final CreateApplicationRequestDto request, final String currentUserId) {
        validateInsured(request);
        validateContractDates(request);

        final PersonalDataDto policyholder = userPersonalDataService.savePersonalData(request.getPolicyholderDto());
        final PersonalDataDto insured = request.isInsuredIsPolicyholder()
            ? policyholder
            : userPersonalDataService.savePersonalData(request.getInsuredDto());

        final ApplicationEntity saved = applicationRepository.save(
            applicationMapper.toEntityWhenCreateApplication(
                request,
                documentUtil.makeDocumentNumber("EP"),
                currentUserId,
                policyholder.getId(),
                insured.getId(),
                LocalDateTime.now()
            )
        );
        final Long applicationId = saved.getId();
        final List<ApplicationBeneficiaryResponseDto> beneficiaries = request.getBeneficiariesDto().stream()
            .map(beneficiary -> toBeneficiaryResponse(beneficiary, policyholder, insured))
            .toList();

        applicationBeneficiaryService.saveApplicationBeneficiaryList(
            buildApplicationBeneficiaryDtoList(beneficiaries, applicationId)
        );
        applicationInsuranceEventService.saveApplicationInsuranceEventList(
            buildApplicationInsuranceEventDtoList(request.getInsuranceEvents(), applicationId)
        );

        return applicationMapper.toDto(
            saved,
            policyholder,
            insured,
            beneficiaries,
            request.getInsuranceEvents(),
            request.isInsuredIsPolicyholder()
        );
    }

    @Override
    @Transactional
    public ApplicationStatusResponseDto updateApplicationStatus(
        final Long applicationId,
        final ApplicationUpdateStatusDto applicationUpdateStatusDto
    ) {
        final ApplicationEntity application = getApplicationEntity(applicationId);
        final ApplicationStatus currentStatus = application.getApplicationStatus();
        final ApplicationStatus newStatus = applicationUpdateStatusDto.getNewStatus();

        if (!statusCanBeUpdatedTo(currentStatus, newStatus)) {
            throw new BusinessException("Application status cannot be changed");
        }

        application.setApplicationStatus(newStatus);
        application.setRejectionReason(
            Objects.equals(newStatus, ApplicationStatus.REJECTED)
                ? applicationUpdateStatusDto.getRejectionReason()
                : null
        );
        application.setUpdatedAt(LocalDateTime.now());
        applicationRepository.save(application);

        return ApplicationStatusResponseDto.builder()
            .id(application.getId())
            .applicationStatus(application.getApplicationStatus())
            .rejectionReason(application.getRejectionReason())
            .build();
    }

    @Override
    public ApplicationEntityDto findApplicationById(final Long applicationId) {
        return applicationMapper.toApplicationEntityDto(getApplicationEntity(applicationId));
    }

    @Override
    public void markAsContractIssued(final Long applicationId, final LocalDateTime updatedAt) {
        final ApplicationEntity application = getApplicationEntity(applicationId);
        if (Objects.equals(application.getApplicationStatus(), ApplicationStatus.APPROVED)) {
            application.setApplicationStatus(ApplicationStatus.CONTRACT_ISSUED);
            application.setUpdatedAt(updatedAt);
            applicationRepository.save(application);
        } else {
            throw new BusinessException("Application isn't approved. Contract can't be issued");
        }
    }

    @Override
    public ApplicationEntityDto findApplicationByIdForContract(final Long applicationId) {
        return applicationMapper.toApplicationEntityDto(
            applicationRepository.findByIdForContract(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found"))
        );
    }

    @Override
    public String getCreatorUserId(final Long applicationId) {
        return applicationRepository.findCreatedByUserId(applicationId)
            .orElseThrow(() -> new NotFoundException("Application not found"));
    }

    @Override
    public List<Long> findAllByCreatedUserId(final String createdUserId) {
        return applicationRepository.findAllIdsByCreatedByUserId(createdUserId);
    }

    private ApplicationEntity getApplicationEntity(final Long applicationId) {
        return applicationRepository.findById(applicationId)
            .orElseThrow(() -> new NotFoundException("Application not found"));
    }

    private ApplicationDto toApplicationDto(final ApplicationEntity application) {
        final Long applicationId = application.getId();
        final List<ApplicationBeneficiaryDto> applicationBeneficiaryDtos =
            applicationBeneficiaryService.getByApplicationId(applicationId);

        final Map<Long, PersonalDataDto> usersPersonalData = userPersonalDataService.getPersonalDataMapByIds(
            application.getPolicyholderId(),
            application.getInsuredId(),
            applicationBeneficiaryDtos,
            ApplicationBeneficiaryDto::getBeneficiaryId
        );

        final Set<InsuranceEvent> events = applicationInsuranceEventService.getByApplicationId(applicationId)
            .stream()
            .map(ApplicationInsuranceEventDto::getInsuranceEvent)
            .collect(Collectors.toSet());

        return toApplicationDto(application, usersPersonalData, applicationBeneficiaryDtos, events);
    }

    private List<ApplicationDto> toApplicationDtoList(final List<ApplicationEntity> applications) {
        if (applications.isEmpty()) {
            return List.of();
        }
        final List<Long> applicationIds = applications.stream()
            .map(ApplicationEntity::getId)
            .toList();

        final Map<Long, List<ApplicationBeneficiaryDto>> groupedApplicationBeneficiaryDtos =
            applicationBeneficiaryService.getByApplicationIds(applicationIds).stream()
                .collect(Collectors.groupingBy(ApplicationBeneficiaryDto::getApplicationId));

        final Map<Long, Set<InsuranceEvent>> eventsByApplicationId =
            applicationInsuranceEventService.getByApplicationIds(applicationIds).stream()
                .collect(Collectors.groupingBy(
                    ApplicationInsuranceEventDto::getApplicationId,
                    Collectors.mapping(ApplicationInsuranceEventDto::getInsuranceEvent, Collectors.toSet())
                ));

        final Set<Long> usersPersonalDataId = new HashSet<>();
        applications.forEach(application -> {
            usersPersonalDataId.add(application.getPolicyholderId());
            usersPersonalDataId.add(application.getInsuredId());
        });
        groupedApplicationBeneficiaryDtos.values().forEach(applicationBeneficiaryDtos ->
            applicationBeneficiaryDtos.forEach(
                applicationBeneficiaryDto -> usersPersonalDataId.add(applicationBeneficiaryDto.getBeneficiaryId())
            )
        );

        final Map<Long, PersonalDataDto> usersPersonalData = userPersonalDataService.getPersonalDataByIds(usersPersonalDataId)
            .stream()
            .collect(Collectors.toMap(PersonalDataDto::getId, Function.identity()));

        return applications.stream()
            .map(application -> toApplicationDto(
                application,
                usersPersonalData,
                groupedApplicationBeneficiaryDtos.getOrDefault(application.getId(), List.of()),
                eventsByApplicationId.getOrDefault(application.getId(), Set.of())
            ))
            .toList();
    }

    private ApplicationDto toApplicationDto(
        final ApplicationEntity application,
        final Map<Long, PersonalDataDto> usersPersonalData,
        final List<ApplicationBeneficiaryDto> applicationBeneficiaryDtos,
        final Set<InsuranceEvent> events
    ) {
        final PersonalDataDto policyholderDto = usersPersonalData.get(application.getPolicyholderId());
        final PersonalDataDto insuredDto = usersPersonalData.get(application.getInsuredId());
        final List<ApplicationBeneficiaryResponseDto> beneficiariesDto = applicationBeneficiaryDtos.stream()
            .map(applicationBeneficiary -> ApplicationBeneficiaryResponseDto.builder()
                .beneficiaryType(applicationBeneficiary.getBeneficiaryType())
                .person(usersPersonalData.get(applicationBeneficiary.getBeneficiaryId()))
                .build())
            .toList();

        return applicationMapper.toDto(
            application,
            policyholderDto,
            insuredDto,
            beneficiariesDto,
            events,
            Objects.equals(application.getPolicyholderId(), application.getInsuredId())
        );
    }

    private List<ApplicationBeneficiaryDto> buildApplicationBeneficiaryDtoList(
        final List<ApplicationBeneficiaryResponseDto> beneficiaries,
        final Long applicationId
    ) {
        return beneficiaries.stream()
            .map(beneficiary -> ApplicationBeneficiaryDto.builder()
                .applicationId(applicationId)
                .beneficiaryId(beneficiary.getPerson().getId())
                .beneficiaryType(beneficiary.getBeneficiaryType())
                .build())
            .toList();
    }

    private List<ApplicationInsuranceEventDto> buildApplicationInsuranceEventDtoList(
        final Set<InsuranceEvent> insuranceEvents,
        final Long applicationId
    ) {
        return insuranceEvents.stream()
            .map(event -> ApplicationInsuranceEventDto.builder()
                .applicationId(applicationId)
                .insuranceEvent(event)
                .build())
            .toList();
    }

    private void validateInsured(final CreateApplicationRequestDto request) {
        if (Objects.isNull(request.getInsuredDto()) && !request.isInsuredIsPolicyholder()) {
            throw new BusinessException("Empty insured data");
        }
    }

    private void validateContractDates(final CreateApplicationRequestDto request) {
        if (!request.getContractEndDate().isAfter(request.getContractStartDate())) {
            throw new BusinessException("Contract end date must be after start date");
        }
    }

    private ApplicationBeneficiaryResponseDto toBeneficiaryResponse(
        final ApplicationBeneficiaryRequestDto beneficiary,
        final PersonalDataDto policyholder,
        final PersonalDataDto insured
    ) {
        return switch (beneficiary.getBeneficiaryType()) {
            case POLICYHOLDER -> buildApplicationBeneficiaryResponseDto(beneficiary, policyholder);
            case INSURED -> buildApplicationBeneficiaryResponseDto(beneficiary, insured);
            case OTHER -> {
                if (Objects.isNull(beneficiary.getBeneficiaryPersonalDataDto())) {
                    throw new BusinessException("Empty beneficiary data");
                }
                yield buildApplicationBeneficiaryResponseDto(
                    beneficiary,
                    userPersonalDataService.savePersonalData(beneficiary.getBeneficiaryPersonalDataDto())
                );
            }
        };
    }

    private ApplicationBeneficiaryResponseDto buildApplicationBeneficiaryResponseDto(
        final ApplicationBeneficiaryRequestDto beneficiary,
        final PersonalDataDto person
    ) {
        return ApplicationBeneficiaryResponseDto.builder()
            .beneficiaryType(beneficiary.getBeneficiaryType())
            .person(person)
            .build();
    }

    private boolean statusCanBeUpdatedTo(final ApplicationStatus currentStatus, final ApplicationStatus newStatus) {
        final Set<ApplicationStatus> allowedStatuses = switch (currentStatus) {
            case PENDING -> Set.of(ApplicationStatus.IN_PROGRESS, ApplicationStatus.APPROVED, ApplicationStatus.REJECTED);
            case IN_PROGRESS -> Set.of(ApplicationStatus.PENDING, ApplicationStatus.APPROVED, ApplicationStatus.REJECTED);
            case APPROVED, REJECTED, CONTRACT_ISSUED -> Set.of();
        };
        return allowedStatuses.contains(newStatus);
    }
}
