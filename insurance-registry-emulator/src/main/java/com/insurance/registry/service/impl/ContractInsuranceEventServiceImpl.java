package com.insurance.registry.service.impl;

import com.insurance.registry.dto.ContractInsuranceEventDto;
import com.insurance.registry.mapper.ContractInsuranceEventMapper;
import com.insurance.registry.repository.ContractInsuranceEventRepository;
import com.insurance.registry.service.ContractInsuranceEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Service
@RequiredArgsConstructor
public class ContractInsuranceEventServiceImpl implements ContractInsuranceEventService {
    private final ContractInsuranceEventRepository contractInsuranceEventRepository;
    private final ContractInsuranceEventMapper contractInsuranceEventMapper;

    @Override
    public void saveContractInsuranceEventList(final List<ContractInsuranceEventDto> dtos) {
        if (dtos.isEmpty()) {
            return;
        }
        contractInsuranceEventRepository.saveAll(contractInsuranceEventMapper.toEntityList(dtos));
    }
}
