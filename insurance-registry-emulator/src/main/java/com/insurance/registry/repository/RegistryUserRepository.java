package com.insurance.registry.repository;

import com.insurance.registry.entity.RegistryUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface RegistryUserRepository extends JpaRepository<RegistryUserEntity, Long> {
}
