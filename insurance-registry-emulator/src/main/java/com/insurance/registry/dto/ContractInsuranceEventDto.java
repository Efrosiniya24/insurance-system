package com.insurance.registry.dto;

import com.insurance.registry.enums.InsuranceEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractInsuranceEventDto {
    private Long registryId;
    private InsuranceEvent insuranceEvent;
}
