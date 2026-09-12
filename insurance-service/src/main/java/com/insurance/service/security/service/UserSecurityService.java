package com.insurance.service.security.service;

import com.insurance.service.dto.AuthenticatedUserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface UserSecurityService {
    Collection<GrantedAuthority> convertRoles(final Jwt jwt);

    AuthenticatedUserDto buildAuthenticatedUser(final Jwt jwt);
}
