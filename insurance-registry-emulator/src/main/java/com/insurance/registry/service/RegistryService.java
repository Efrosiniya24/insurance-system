package com.insurance.registry.service;

import com.insurance.registry.dto.RegisterContractRequestDto;
import com.insurance.registry.dto.RegisterContractResponseDto;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
public interface RegistryService {
    RegisterContractResponseDto register(final RegisterContractRequestDto request);
}
