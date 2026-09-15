package com.insurance.service.model.application.dto;

import com.insurance.service.enums.ApplicationStatus;

import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
public record ApplicationFilter(
    ApplicationStatus status,
    LocalDate createdFrom,
    LocalDate createdTo
) {
}
