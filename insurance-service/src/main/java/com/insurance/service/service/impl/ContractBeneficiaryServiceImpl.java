package com.insurance.service.service.impl;

import com.insurance.service.model.contract.dto.ContractBeneficiaryDto;
import com.insurance.service.model.contract.mapper.ContractBeneficiaryMapper;
import com.insurance.service.model.contract.repository.ContractBeneficiaryRepository;
import com.insurance.service.service.ContractBeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Service
@RequiredArgsConstructor
public class ContractBeneficiaryServiceImpl implements ContractBeneficiaryService {
    private final ContractBeneficiaryRepository contractBeneficiaryRepository;
    private final ContractBeneficiaryMapper contractBeneficiaryMapper;

    @Override
    public List<ContractBeneficiaryDto> saveContractBeneficiaryList(final List<ContractBeneficiaryDto> dtos) {
        return contractBeneficiaryMapper.toDtoList(
            contractBeneficiaryRepository.saveAll(contractBeneficiaryMapper.toEntityList(dtos))
        );
    }

    @Override
    public List<ContractBeneficiaryDto> getByContractId(final Long contractId) {
        return contractBeneficiaryMapper.toDtoList(
            contractBeneficiaryRepository.findAllByContractId(contractId)
        );
    }

    @Override
    public List<ContractBeneficiaryDto> getByContractIds(final List<Long> contractIds) {
        if (contractIds.isEmpty()) {
            return List.of();
        }
        return contractBeneficiaryMapper.toDtoList(
            contractBeneficiaryRepository.findAllByContractIdIn(contractIds)
        );
    }
}
