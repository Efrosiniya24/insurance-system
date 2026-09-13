package com.insurance.service.service;

import com.insurance.service.model.application.dto.ApplicationInsuranceEventDto;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
public interface ApplicationInsuranceEventService {
    /**
     * Persists an insurance event of an application
     *
     * @param applicationInsuranceEventDto application id and insurance event
     * @return saved insurance event
     */
    ApplicationInsuranceEventDto saveApplicationInsuranceEvent(ApplicationInsuranceEventDto applicationInsuranceEventDto);

    /**
     * Persists insurance events of an application
     *
     * @param dtos application id and insurance event for each dto
     * @return saved insurance events
     */
    List<ApplicationInsuranceEventDto> saveApplicationInsuranceEventList(List<ApplicationInsuranceEventDto> dtos);

    /**
     * Loads insurance events of one application
     *
     * @param applicationId application id
     * @return insurance events of the application
     */
    List<ApplicationInsuranceEventDto> getByApplicationId(Long applicationId);

    /**
     * Loads insurance events of several applications
     *
     * @param applicationIds application ids
     * @return insurance events of the given applications
     */
    List<ApplicationInsuranceEventDto> getByApplicationIds(List<Long> applicationIds);
}
