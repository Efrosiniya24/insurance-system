package com.insurance.service.model.contract.repository;

import com.insurance.service.model.contract.entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    Optional<ContractEntity> findByApplicationId(Long applicationId);

    Page<ContractEntity> findAllByApplicationIdIn(List<Long> applicationIds, Pageable pageable);
}
