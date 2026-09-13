package com.insurance.service.model.application.dto;

import com.insurance.service.enums.InsuranceEvent;
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
public class ApplicationInsuranceEventDto {
    private Long id;
    private Long applicationId;
    private InsuranceEvent insuranceEvent;
}
