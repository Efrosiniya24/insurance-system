package com.insurance.registry.service;

import com.insurance.registry.dto.ContractBeneficiaryDto;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
public interface ContractBeneficiaryService {
    void saveContractBeneficiaryList(List<ContractBeneficiaryDto> dtos);
}
