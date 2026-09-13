package com.insurance.service.security.service;

import com.insurance.service.security.dto.AuthenticatedUserDto;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface UserSecurityService {
    /**
     * Maps a validated JWT to the API principal
     *
     * @param jwt token issued by Keycloak
     * @return user id, username and roles
     */
    AuthenticatedUserDto buildAuthenticatedUser(final Jwt jwt);
}
