package com.insurance.service.security.service;

import com.insurance.service.security.dto.AuthenticatedUserDto;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
public interface AccessService {
    /**
     * Allows an underwriter or the resource owner. Otherwise, throws ForbiddenException.
     *
     * @param currentUser     authenticated user
     * @param createdByUserId owner id stored on the resource
     */
    void ownerUserOrUnderwriter(AuthenticatedUserDto currentUser, String createdByUserId);
}
