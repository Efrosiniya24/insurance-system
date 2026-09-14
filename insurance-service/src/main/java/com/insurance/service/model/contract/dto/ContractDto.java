package com.insurance.service.model.contract.dto;

import com.insurance.service.enums.ContractRegistryStatus;
import com.insurance.service.enums.InsuranceEvent;
import com.insurance.service.enums.PremiumFrequency;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryResponseDto;
import com.insurance.service.model.user.dto.PersonalDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDto {
    private Long id;
    private String contractNumber;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private PersonalDataDto policyholder;
    private PersonalDataDto insured;
    private boolean insuredIsPolicyholder;
    private List<ApplicationBeneficiaryResponseDto> beneficiaries;
    private BigDecimal sumInsured;
    private BigDecimal insurancePremium;
    private PremiumFrequency premiumFrequency;
    private Long applicationId;
    private LocalDateTime createdAt;
    private Set<InsuranceEvent> insuranceEvents;
    private ContractRegistryStatus registryStatus;
    private String issuedByUserId;
    private boolean alreadyIssued;
    private boolean issuedByAnotherUser;
}