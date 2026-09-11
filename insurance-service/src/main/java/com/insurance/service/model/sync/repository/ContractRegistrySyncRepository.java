package com.insurance.service.model.sync.repository;

import com.insurance.service.model.sync.entity.ContractRegistrySyncEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ContractRegistrySyncRepository extends JpaRepository<ContractRegistrySyncEntity, Long> {
}
