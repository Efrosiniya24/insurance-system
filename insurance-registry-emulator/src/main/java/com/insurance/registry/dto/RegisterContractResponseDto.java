package com.insurance.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterContractResponseDto {
    private String registryNumber;
    private LocalDate registryDate;
    private String contractNumber;
    private boolean alreadyRegistered;
}