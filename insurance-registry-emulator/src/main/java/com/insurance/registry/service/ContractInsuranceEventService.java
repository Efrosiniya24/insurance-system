package com.insurance.registry.service;

import com.insurance.registry.dto.ContractInsuranceEventDto;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
public interface ContractInsuranceEventService {
    void saveContractInsuranceEventList(List<ContractInsuranceEventDto> dtos);
}
