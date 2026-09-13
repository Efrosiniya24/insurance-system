package com.insurance.service.service;

import com.insurance.service.model.application.dto.ApplicationDto;
import com.insurance.service.model.application.dto.ApplicationUpdateStatusDto;
import com.insurance.service.model.application.dto.CreateApplicationRequestDto;
import com.insurance.service.security.dto.AuthenticatedUserDto;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
public interface ApplicationService {
    /**
     * Returns an application if the caller is an underwriter or the owner. Otherwise throws ForbiddenException.
     *
     * @param applicationId application id
     * @param currentUser   authenticated user
     * @return application dto
     */
    ApplicationDto getApplication(Long applicationId, AuthenticatedUserDto currentUser);

    /**
     * Returns own applications for a policyholder and all applications for an underwriter
     *
     * @param currentUser authenticated user
     * @return applications dtos
     */
    List<ApplicationDto> getApplicationList(AuthenticatedUserDto currentUser);

    /**
     * Creates an application with PENDING status
     *
     * @param createApplicationRequestDto application payload from the client
     * @param currentUserId               authenticated user id
     * @return created application
     */
    ApplicationDto createApplication(
        CreateApplicationRequestDto createApplicationRequestDto,
        String currentUserId
    );

    ApplicationDto approveApplication(ApplicationUpdateStatusDto applicationUpdateStatusDto);
}
