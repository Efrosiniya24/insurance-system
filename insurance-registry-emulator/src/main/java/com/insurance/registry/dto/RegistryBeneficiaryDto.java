package com.insurance.registry.dto;

import com.insurance.registry.enums.BeneficiaryType;
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
public class RegistryBeneficiaryDto {
    private BeneficiaryType beneficiaryType;
    private RegistryPersonDataDto person;
}
