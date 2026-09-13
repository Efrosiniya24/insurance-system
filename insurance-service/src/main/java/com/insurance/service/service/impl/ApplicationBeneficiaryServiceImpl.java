package com.insurance.service.service.impl;

import com.insurance.service.model.application.dto.ApplicationBeneficiaryDto;
import com.insurance.service.model.application.mapper.ApplicationBeneficiaryMapper;
import com.insurance.service.model.application.repository.ApplicationBeneficiaryRepository;
import com.insurance.service.service.ApplicationBeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Service
@RequiredArgsConstructor
public class ApplicationBeneficiaryServiceImpl implements ApplicationBeneficiaryService {
    private final ApplicationBeneficiaryRepository applicationBeneficiaryRepository;
    private final ApplicationBeneficiaryMapper applicationBeneficiaryMapper;

    @Override
    public ApplicationBeneficiaryDto saveApplicationBeneficiary(final ApplicationBeneficiaryDto dto) {
        return applicationBeneficiaryMapper.toDto(
            applicationBeneficiaryRepository.save(applicationBeneficiaryMapper.toEntity(dto))
        );
    }

    @Override
    public List<ApplicationBeneficiaryDto> saveApplicationBeneficiaryList(final List<ApplicationBeneficiaryDto> dtos) {
        return applicationBeneficiaryMapper.toDtoList(
            applicationBeneficiaryRepository.saveAll(applicationBeneficiaryMapper.toEntityList(dtos))
        );
    }

    @Override
    public List<ApplicationBeneficiaryDto> getByApplicationId(final Long applicationId) {
        return applicationBeneficiaryMapper.toDtoList(
            applicationBeneficiaryRepository.findAllByApplicationId(applicationId)
        );
    }

    @Override
    public List<ApplicationBeneficiaryDto> getByApplicationIds(final List<Long> applicationIds) {
        if (applicationIds.isEmpty()) {
            return List.of();
        }
        return applicationBeneficiaryMapper.toDtoList(
            applicationBeneficiaryRepository.findAllByApplicationIdIn(applicationIds)
        );
    }
}
