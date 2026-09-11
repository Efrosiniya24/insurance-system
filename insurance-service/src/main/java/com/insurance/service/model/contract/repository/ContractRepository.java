package com.insurance.service.model.contract.repository;

import com.insurance.service.model.contract.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
}
