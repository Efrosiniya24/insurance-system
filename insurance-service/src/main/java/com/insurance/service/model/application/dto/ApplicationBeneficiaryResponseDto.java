package com.insurance.service.model.application.dto;

import com.insurance.service.enums.BeneficiaryType;
import com.insurance.service.model.user.dto.PersonalDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationBeneficiaryResponseDto {
    private BeneficiaryType beneficiaryType;
    private PersonalDataDto person;
}
