package com.insurance.service.model.contract.dto;

import com.insurance.service.enums.BeneficiaryType;
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
public class ContractBeneficiaryDto {
    private Long id;
    private Long contractId;
    private Long beneficiaryId;
    private BeneficiaryType beneficiaryType;
}
