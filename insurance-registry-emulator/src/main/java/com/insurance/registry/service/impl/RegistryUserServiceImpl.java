package com.insurance.registry.service.impl;

import com.insurance.registry.dto.RegistryPersonDataDto;
import com.insurance.registry.mapper.RegistryUserMapper;
import com.insurance.registry.repository.RegistryUserRepository;
import com.insurance.registry.service.RegistryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Service
@RequiredArgsConstructor
public class RegistryUserServiceImpl implements RegistryUserService {
    private final RegistryUserRepository registryUserRepository;
    private final RegistryUserMapper registryUserMapper;

    @Override
    public Long save(final RegistryPersonDataDto person) {
        return registryUserRepository.save(registryUserMapper.toEntity(person)).getId();
    }
}
