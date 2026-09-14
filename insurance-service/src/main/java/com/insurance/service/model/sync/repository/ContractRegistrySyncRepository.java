package com.insurance.service.model.sync.repository;

import com.insurance.service.model.sync.entity.ContractRegistrySyncEntity;
import com.insurance.service.model.sync.projection.ContractRegistryStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ContractRegistrySyncRepository extends JpaRepository<ContractRegistrySyncEntity, Long> {
    Optional<ContractRegistrySyncEntity> findByContractId(Long contractId);

    @Query("select c.contractId as contractId, c.contractRegistryStatus as contractRegistryStatus " +
        "from ContractRegistrySyncEntity c " +
        "where c.contractId in :contractIds")
    List<ContractRegistryStatusProjection> findAllByContractIdIn(List<Long> contractIds);
}
