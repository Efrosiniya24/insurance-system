package com.insurance.registry.service;

import com.insurance.registry.dto.RegistryPersonDataDto;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
public interface RegistryUserService {
    Long save(RegistryPersonDataDto person);
}
