package com.insurance.registry.repository;

import com.insurance.registry.entity.RegistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface RegistryRepository extends JpaRepository<RegistryEntity, Long> {
    Optional<RegistryEntity> findByContractNumber(String contractNumber);
}
