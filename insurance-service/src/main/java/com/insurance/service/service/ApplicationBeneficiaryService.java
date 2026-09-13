package com.insurance.service.service;

import com.insurance.service.model.application.dto.ApplicationBeneficiaryDto;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
public interface ApplicationBeneficiaryService {
    /**
     * Persists a beneficiary link of an application
     *
     * @param applicationBeneficiaryDto application id, person id and beneficiary type
     * @return saved beneficiary link
     */
    ApplicationBeneficiaryDto saveApplicationBeneficiary(ApplicationBeneficiaryDto applicationBeneficiaryDto);

    /**
     * Persists beneficiary links of an application
     *
     * @param dtos application id, person id and beneficiary type for each link
     * @return saved beneficiary links
     */
    List<ApplicationBeneficiaryDto> saveApplicationBeneficiaryList(List<ApplicationBeneficiaryDto> dtos);

    /**
     * Loads beneficiary links of one application
     *
     * @param applicationId application id
     * @return beneficiary links of the application
     */
    List<ApplicationBeneficiaryDto> getByApplicationId(Long applicationId);

    /**
     * Loads beneficiary links of several applications
     *
     * @param applicationIds application ids
     * @return beneficiary links of the given applications
     */
    List<ApplicationBeneficiaryDto> getByApplicationIds(List<Long> applicationIds);
}
