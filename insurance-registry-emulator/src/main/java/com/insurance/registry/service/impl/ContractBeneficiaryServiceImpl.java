package com.insurance.registry.service.impl;

import com.insurance.registry.dto.ContractBeneficiaryDto;
import com.insurance.registry.mapper.ContractBeneficiaryMapper;
import com.insurance.registry.repository.ContractBeneficiaryRepository;
import com.insurance.registry.service.ContractBeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Service
@RequiredArgsConstructor
public class ContractBeneficiaryServiceImpl implements ContractBeneficiaryService {
    private final ContractBeneficiaryRepository contractBeneficiaryRepository;
    private final ContractBeneficiaryMapper contractBeneficiaryMapper;

    @Override
    public void saveContractBeneficiaryList(final List<ContractBeneficiaryDto> dtos) {
        if (dtos.isEmpty()) {
            return;
        }
        contractBeneficiaryRepository.saveAll(contractBeneficiaryMapper.toEntityList(dtos));
    }
}
