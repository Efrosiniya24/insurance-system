package com.insurance.service.model.contract.repository;

import com.insurance.service.model.contract.entity.ContractBeneficiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ContractBeneficiaryRepository extends JpaRepository<ContractBeneficiaryEntity, Long> {
    List<ContractBeneficiaryEntity> findAllByContractId(Long contractId);

    List<ContractBeneficiaryEntity> findAllByContractIdIn(List<Long> contractIds);
}
