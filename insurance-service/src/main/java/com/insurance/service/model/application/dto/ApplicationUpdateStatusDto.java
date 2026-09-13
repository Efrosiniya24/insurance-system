package com.insurance.service.model.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationUpdateStatusDto {
    private Long id;
    private String newStatus;
}
