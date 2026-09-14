package com.insurance.service.model.contract.mapper;

import com.insurance.service.enums.ContractRegistryStatus;
import com.insurance.service.enums.InsuranceEvent;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryResponseDto;
import com.insurance.service.model.application.dto.ApplicationEntityDto;
import com.insurance.service.model.contract.dto.ContractDto;
import com.insurance.service.model.contract.entity.ContractEntity;
import com.insurance.service.model.user.dto.PersonalDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contractStartDate", source = "application.contractStartDate")
    @Mapping(target = "contractEndDate", source = "application.contractEndDate")
    @Mapping(target = "policyholderId", source = "application.policyholderId")
    @Mapping(target = "insuredId", source = "application.insuredId")
    @Mapping(target = "sumInsured", source = "application.sumInsured")
    @Mapping(target = "insurancePremium", source = "application.insurancePremium")
    @Mapping(target = "premiumFrequency", source = "application.premiumFrequency")
    @Mapping(target = "applicationId", source = "application.id")
    @Mapping(target = "createdAt", source = "now")
    ContractEntity toEntityWhenIssueContract(
        ApplicationEntityDto application,
        String contractNumber,
        String issuedByUserId,
        LocalDateTime now
    );

    @Mapping(target = "id", source = "contract.id")
    @Mapping(target = "applicationId", source = "contract.applicationId")
    ContractDto toDto(
        ContractEntity contract,
        PersonalDataDto policyholder,
        PersonalDataDto insured,
        List<ApplicationBeneficiaryResponseDto> beneficiaries,
        Set<InsuranceEvent> insuranceEvents,
        boolean insuredIsPolicyholder,
        ContractRegistryStatus registryStatus,
        boolean alreadyIssued,
        boolean issuedByAnotherUser
    );
}
