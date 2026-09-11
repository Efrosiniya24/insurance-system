package com.insurance.registry.repository;

import com.insurance.registry.entity.RegistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface RegistryRepository extends JpaRepository<RegistryEntity, Long> {
}
