package com.insurance.service.service.impl;

import com.insurance.service.model.user.dto.PersonalDataDto;
import com.insurance.service.model.user.mapper.UserPersonalDataMapper;
import com.insurance.service.model.user.repository.UserPersonalDataRepository;
import com.insurance.service.service.UserPersonalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public <T> Map<Long, PersonalDataDto> getPersonalDataMapByIds(
        final Long policyholderId,
        final Long insuredId,
        final List<T> linksWithBeneficiary,
        final Function<T, Long> beneficiaryIdExtractor
    ) {
        final Set<Long> personalDataIds = new HashSet<>();
        personalDataIds.add(policyholderId);
        personalDataIds.add(insuredId);
        linksWithBeneficiary.forEach(beneficiary -> personalDataIds.add(beneficiaryIdExtractor.apply(beneficiary)));
        return getPersonalDataByIds(personalDataIds)
            .stream()
            .collect(Collectors.toMap(PersonalDataDto::getId, Function.identity()));
    }
}
