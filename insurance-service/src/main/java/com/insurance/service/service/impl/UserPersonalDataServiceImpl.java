package com.insurance.service.service.impl;

import com.insurance.service.model.user.dto.PersonalDataDto;
import com.insurance.service.model.user.mapper.UserPersonalDataMapper;
import com.insurance.service.model.user.repository.UserPersonalDataRepository;
import com.insurance.service.service.UserPersonalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Service
@RequiredArgsConstructor
public class UserPersonalDataServiceImpl implements UserPersonalDataService {
    private final UserPersonalDataRepository userPersonalDataRepository;
    private final UserPersonalDataMapper userPersonalDataMapper;

    @Override
    public PersonalDataDto savePersonalData(final PersonalDataDto dto) {
        return userPersonalDataMapper.toDto(
            userPersonalDataRepository.save(userPersonalDataMapper.toEntity(dto))
        );
    }

    @Override
    public List<PersonalDataDto> savePersonalDataList(final List<PersonalDataDto> dtos) {
        return userPersonalDataMapper.toDtoList(
            userPersonalDataRepository.saveAll(userPersonalDataMapper.toEntityList(dtos))
        );
    }

    @Override
    public List<PersonalDataDto> getPersonalDataByIds(final Collection<Long> ids) {
        return userPersonalDataMapper.toDtoList(userPersonalDataRepository.findAllById(ids));
    }
}
