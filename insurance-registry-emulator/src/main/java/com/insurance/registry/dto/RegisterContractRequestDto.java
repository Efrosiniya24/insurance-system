package com.insurance.registry.dto;

import com.insurance.registry.enums.InsuranceEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterContractRequestDto {
    private String contractNumber;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private BigDecimal sumInsured;
    private RegistryPersonDataDto insured;
    private RegistryPersonDataDto policyholder;
    private List<RegistryBeneficiaryDto> beneficiaries;
    private Set<InsuranceEvent> insuranceEvents;
}