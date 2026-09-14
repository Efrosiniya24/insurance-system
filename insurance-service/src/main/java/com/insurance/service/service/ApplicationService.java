package com.insurance.service.service;

import com.insurance.service.model.application.dto.ApplicationDto;
import com.insurance.service.model.application.dto.ApplicationEntityDto;
import com.insurance.service.model.application.dto.ApplicationStatusResponseDto;
import com.insurance.service.model.application.dto.ApplicationUpdateStatusDto;
import com.insurance.service.model.application.dto.CreateApplicationRequestDto;
import com.insurance.service.security.dto.AuthenticatedUserDto;

import java.time.LocalDateTime;
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

    /**
     * Changes application status if allowed
     *
     * @param applicationId              application id
     * @param applicationUpdateStatusDto new status and rejection reason
     * @return application id, its new status and rejection reason
     */
    ApplicationStatusResponseDto updateApplicationStatus(
        Long applicationId,
        ApplicationUpdateStatusDto applicationUpdateStatusDto
    );

    /**
     * Finds application by id
     *
     * @param applicationId application id
     * @return application data
     */
    ApplicationEntityDto findApplicationById(Long applicationId);

    /**
     * Marks application as CONTRACT_ISSUED after a contract is created
     *
     * @param applicationId application id
     * @param updatedAt     update timestamp
     */
    void markAsContractIssued(Long applicationId, LocalDateTime updatedAt);

    /**
     * Locks the application row and returns its data for contract issuance
     *
     * @param applicationId application id
     * @return application data
     */
    ApplicationEntityDto findApplicationByIdForContract(Long applicationId);

    /**
     * Returns the id of the user who created the application
     *
     * @param applicationId application id
     * @return creator user id
     */
    String getCreatorUserId(Long applicationId);

    /**
     * Returns ids of applications created by the given user
     *
     * @param createdUserId creator user id
     * @return application ids
     */
    List<Long> findAllByCreatedUserId(String createdUserId);
}
