package com.insurance.service.client.registry.dto;

import com.insurance.service.enums.BeneficiaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryBeneficiaryRequestDto {
    private BeneficiaryType beneficiaryType;
    private RegistryPersonRequestDto person;
}
