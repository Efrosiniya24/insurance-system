package com.insurance.service.service;

import com.insurance.service.model.contract.dto.ContractBeneficiaryDto;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
public interface ContractBeneficiaryService {
    /**
     * Persists beneficiary links of a contract
     *
     * @param dtos contract id, person id and beneficiary type for each link
     * @return saved beneficiary links
     */
    List<ContractBeneficiaryDto> saveContractBeneficiaryList(List<ContractBeneficiaryDto> dtos);

    /**
     * Loads beneficiary links of one contract
     *
     * @param contractId contract id
     * @return beneficiary links of the contract
     */
    List<ContractBeneficiaryDto> getByContractId(Long contractId);

    /**
     * Loads beneficiary links of contracts
     *
     * @param contractIds contract ids
     * @return beneficiary links of the given contracts
     */
    List<ContractBeneficiaryDto> getByContractIds(List<Long> contractIds);
}
