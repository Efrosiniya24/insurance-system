package com.insurance.service.client.registry.dto;

import com.insurance.service.enums.InsuranceEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterContractRequestDto {
    private String contractNumber;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private BigDecimal sumInsured;
    private RegistryPersonRequestDto insured;
    private RegistryPersonRequestDto policyholder;
    private List<RegistryBeneficiaryRequestDto> beneficiaries;
    private Set<InsuranceEvent> insuranceEvents;
}
