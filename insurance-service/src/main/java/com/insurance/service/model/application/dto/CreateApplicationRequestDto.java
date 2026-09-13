package com.insurance.service.model.application.dto;

import com.insurance.service.enums.InsuranceEvent;
import com.insurance.service.enums.PremiumFrequency;
import com.insurance.service.model.user.dto.PersonalDataDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationRequestDto {
    @Valid
    @NotNull
    private PersonalDataDto policyholderDto;

    @Valid
    private PersonalDataDto insuredDto;

    private boolean insuredIsPolicyholder;

    @Valid
    @NotNull
    private List<ApplicationBeneficiaryRequestDto> beneficiariesDto;

    @NotEmpty
    private Set<InsuranceEvent> insuranceEvents;

    @NotNull
    private BigDecimal sumInsured;

    @NotNull
    private BigDecimal insurancePremium;

    @NotNull
    private PremiumFrequency premiumFrequency;

    @NotNull
    private LocalDate contractStartDate;

    @NotNull
    private LocalDate contractEndDate;
}
