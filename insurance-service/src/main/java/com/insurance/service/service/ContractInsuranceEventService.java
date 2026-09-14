package com.insurance.service.service;

import com.insurance.service.model.contract.dto.ContractInsuranceEventDto;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
public interface ContractInsuranceEventService {
    /**
     * Persists insurance events of a contract
     *
     * @param dtos contract id and insurance event for each dto
     * @return saved insurance events
     */
    List<ContractInsuranceEventDto> saveContractInsuranceEventList(List<ContractInsuranceEventDto> dtos);

    /**
     * Loads insurance events of one contract
     *
     * @param contractId contract id
     * @return insurance events of the contract
     */
    List<ContractInsuranceEventDto> getByContractId(Long contractId);

    /**
     * Loads insurance events of contracts
     *
     * @param contractIds contract ids
     * @return insurance events of the given contracts
     */
    List<ContractInsuranceEventDto> getByContractIds(List<Long> contractIds);
}
