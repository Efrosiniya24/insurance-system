package com.insurance.service.service.impl;

import com.insurance.service.model.contract.dto.ContractInsuranceEventDto;
import com.insurance.service.model.contract.mapper.ContractInsuranceEventMapper;
import com.insurance.service.model.contract.repository.ContractInsuranceEventRepository;
import com.insurance.service.service.ContractInsuranceEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Service
@RequiredArgsConstructor
public class ContractInsuranceEventServiceImpl implements ContractInsuranceEventService {
    private final ContractInsuranceEventRepository contractInsuranceEventRepository;
    private final ContractInsuranceEventMapper contractInsuranceEventMapper;

    @Override
    public List<ContractInsuranceEventDto> saveContractInsuranceEventList(final List<ContractInsuranceEventDto> dtos) {
        return contractInsuranceEventMapper.toDtoList(
            contractInsuranceEventRepository.saveAll(contractInsuranceEventMapper.toEntityList(dtos))
        );
    }

    @Override
    public List<ContractInsuranceEventDto> getByContractId(final Long contractId) {
        return contractInsuranceEventMapper.toDtoList(
            contractInsuranceEventRepository.findAllByContractId(contractId)
        );
    }

    @Override
    public List<ContractInsuranceEventDto> getByContractIds(final List<Long> contractIds) {
        if (contractIds.isEmpty()) {
            return List.of();
        }
        return contractInsuranceEventMapper.toDtoList(
            contractInsuranceEventRepository.findAllByContractIdIn(contractIds)
        );
    }
}
