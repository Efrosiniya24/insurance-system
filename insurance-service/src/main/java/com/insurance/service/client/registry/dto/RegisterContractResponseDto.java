package com.insurance.service.client.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterContractResponseDto {
    private String registryNumber;
    private LocalDate registryDate;
    private String contractNumber;
    private boolean alreadyRegistered;
}
