package com.insurance.service.service.impl;

import com.insurance.service.model.application.dto.ApplicationInsuranceEventDto;
import com.insurance.service.model.application.mapper.ApplicationInsuranceEventMapper;
import com.insurance.service.model.application.repository.ApplicationInsuranceEventRepository;
import com.insurance.service.service.ApplicationInsuranceEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Service
@RequiredArgsConstructor
public class ApplicationInsuranceEventServiceImpl implements ApplicationInsuranceEventService {
    private final ApplicationInsuranceEventRepository applicationInsuranceEventRepository;
    private final ApplicationInsuranceEventMapper applicationInsuranceEventMapper;

    @Override
    public ApplicationInsuranceEventDto saveApplicationInsuranceEvent(final ApplicationInsuranceEventDto dto) {
        return applicationInsuranceEventMapper.toDto(
            applicationInsuranceEventRepository.save(applicationInsuranceEventMapper.toEntity(dto))
        );
    }

    @Override
    public List<ApplicationInsuranceEventDto> saveApplicationInsuranceEventList(final List<ApplicationInsuranceEventDto> dtos) {
        return applicationInsuranceEventMapper.toDtoList(
            applicationInsuranceEventRepository.saveAll(applicationInsuranceEventMapper.toEntityList(dtos))
        );
    }

    @Override
    public List<ApplicationInsuranceEventDto> getByApplicationId(final Long applicationId) {
        return applicationInsuranceEventMapper.toDtoList(
            applicationInsuranceEventRepository.findAllByApplicationId(applicationId)
        );
    }

    @Override
    public List<ApplicationInsuranceEventDto> getByApplicationIds(final List<Long> applicationIds) {
        if (applicationIds.isEmpty()) {
            return List.of();
        }
        return applicationInsuranceEventMapper.toDtoList(
            applicationInsuranceEventRepository.findAllByApplicationIdIn(applicationIds)
        );
    }
}
