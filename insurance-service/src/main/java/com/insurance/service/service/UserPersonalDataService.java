package com.insurance.service.service;

import com.insurance.service.model.user.dto.PersonalDataDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
public interface UserPersonalDataService {
    /**
     * Persists a personal-data snapshot and returns it with a generated id
     *
     * @param dto personal data from the client
     * @return saved personal data
     */
    PersonalDataDto savePersonalData(final PersonalDataDto dto);

    /**
     * Persists several personal-data snapshots
     *
     * @param dtos personal data from the client
     * @return saved personal data
     */
    List<PersonalDataDto> savePersonalDataList(List<PersonalDataDto> dtos);

    /**
     * Loads personal-data snapshots by ids
     *
     * @param ids personal data ids
     * @return personal data found for the given ids
     */
    List<PersonalDataDto> getPersonalDataByIds(Collection<Long> ids);

    /**
     * Loads personal-data of a policyholder, an insured and beneficiaries
     *
     * @param policyholderId         policyholder personal-data id
     * @param insuredId              insured personal-data id
     * @param linksWithBeneficiary   beneficiary links
     * @param beneficiaryIdExtractor extracts a personal-data id from a link
     * @return personal data by id
     */
    <T> Map<Long, PersonalDataDto> getPersonalDataMapByIds(
        Long policyholderId,
        Long insuredId,
        List<T> linksWithBeneficiary,
        Function<T, Long> beneficiaryIdExtractor
    );
}
