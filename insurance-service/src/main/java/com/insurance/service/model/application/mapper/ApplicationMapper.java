package com.insurance.service.model.application.mapper;

import com.insurance.service.enums.InsuranceEvent;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryResponseDto;
import com.insurance.service.model.application.dto.ApplicationDto;
import com.insurance.service.model.application.dto.ApplicationEntityDto;
import com.insurance.service.model.application.dto.CreateApplicationRequestDto;
import com.insurance.service.model.application.entity.ApplicationEntity;
import com.insurance.service.model.user.dto.PersonalDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicationStatus", constant = "PENDING")
    @Mapping(target = "rejectionReason", ignore = true)
    @Mapping(target = "createdAt", source = "now")
    @Mapping(target = "updatedAt", source = "now")
    ApplicationEntity toEntityWhenCreateApplication(
        CreateApplicationRequestDto request,
        String applicationNumber,
        String createdByUserId,
        Long policyholderId,
        Long insuredId,
        LocalDateTime now
    );

    @Mapping(target = "id", source = "applicationEntity.id")
    ApplicationDto toDto(
        ApplicationEntity applicationEntity,
        PersonalDataDto policyholder,
        PersonalDataDto insured,
        List<ApplicationBeneficiaryResponseDto> beneficiaries,
        Set<InsuranceEvent> insuranceEvents,
        boolean insuredIsPolicyholder
    );

    ApplicationEntityDto toApplicationEntityDto(ApplicationEntity applicationEntity);
}
