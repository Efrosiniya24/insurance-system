package com.insurance.service.model.application.dto;

import com.insurance.service.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationStatusResponseDto {
    private Long id;
    private ApplicationStatus applicationStatus;
    private String rejectionReason;
}
