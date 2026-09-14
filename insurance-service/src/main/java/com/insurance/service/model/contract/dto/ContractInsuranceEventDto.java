package com.insurance.service.model.contract.dto;

import com.insurance.service.enums.InsuranceEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractInsuranceEventDto {
    private Long id;
    private Long contractId;
    private InsuranceEvent insuranceEvent;
}
