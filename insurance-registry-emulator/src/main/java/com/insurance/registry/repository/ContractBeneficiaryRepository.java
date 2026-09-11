package com.insurance.registry.repository;

import com.insurance.registry.entity.ContractBeneficiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ContractBeneficiaryRepository extends JpaRepository<ContractBeneficiaryEntity, Long> {
}
