package com.insurance.service.model.application.dto;

import com.insurance.service.enums.ApplicationStatus;
import com.insurance.service.enums.InsuranceEvent;
import com.insurance.service.enums.PremiumFrequency;
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
 * @since 12.09.2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDto {
    private Long id;
    private String applicationNumber;
    private ApplicationStatus applicationStatus;
    private boolean insuredIsPolicyholder;
    private PersonalDataDto policyholder;
    private PersonalDataDto insured;
    private List<ApplicationBeneficiaryResponseDto> beneficiaries;
    private Set<InsuranceEvent> insuranceEvents;
    private BigDecimal sumInsured;
    private BigDecimal insurancePremium;
    private PremiumFrequency premiumFrequency;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
