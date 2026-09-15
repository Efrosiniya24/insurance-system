package com.insurance.service.model.contract.dto;

import com.insurance.service.enums.ContractRegistryStatus;

import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
public record ContractFilter(
    ContractRegistryStatus registryStatus,
    LocalDate createdFrom,
    LocalDate createdTo,
    LocalDate contractStartDate,
    LocalDate contractEndDate
) {
}
