package com.insurance.service.model.application.dto;

import com.insurance.service.enums.BeneficiaryType;
import com.insurance.service.model.user.dto.PersonalDataDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationBeneficiaryRequestDto {
    @NotNull
    private BeneficiaryType beneficiaryType;

    @Valid
    private PersonalDataDto beneficiaryPersonalDataDto;
}
