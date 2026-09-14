package com.insurance.service.model.contract.repository;

import com.insurance.service.model.contract.entity.ContractInsuranceEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ContractInsuranceEventRepository extends JpaRepository<ContractInsuranceEventEntity, Long> {
    List<ContractInsuranceEventEntity> findAllByContractId(Long contractId);

    List<ContractInsuranceEventEntity> findAllByContractIdIn(List<Long> contractIds);
}
