package com.insurance.service.model.sync.projection;

import com.insurance.service.enums.ContractRegistryStatus;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
public interface ContractRegistryStatusProjection {
    Long getContractId();

    ContractRegistryStatus getContractRegistryStatus();
}
