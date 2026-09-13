package com.insurance.service.model.application.dto;

import com.insurance.service.enums.BeneficiaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationBeneficiaryDto {
    private Long id;
    private Long applicationId;
    private Long beneficiaryId;
    private BeneficiaryType beneficiaryType;
}
