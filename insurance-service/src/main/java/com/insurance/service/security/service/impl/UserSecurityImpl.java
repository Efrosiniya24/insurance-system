package com.insurance.service.security.service.impl;

import com.insurance.service.security.dto.AuthenticatedUserDto;
import com.insurance.service.security.enums.UserRole;
import com.insurance.service.security.service.UserSecurityService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
@Service
public class UserSecurityImpl implements UserSecurityService {
    private static final String REALM_ACCESS_KEY = "realm_access";
    private static final String ROLES_KEY = "roles";
    private static final String USERNAME_KEY = "preferred_username";
    private static final Set<String> BUSINESS_ROLES = Arrays.stream(UserRole.values())
        .map(Enum::name)
        .collect(Collectors.toSet());

    @Override
    public AuthenticatedUserDto buildAuthenticatedUser(final Jwt jwt) {
        return AuthenticatedUserDto.builder()
            .id(jwt.getSubject())
            .username(jwt.getClaimAsString(USERNAME_KEY))
            .roles(extractBusinessRoles(jwt))
            .build();
    }

    private Set<UserRole> extractBusinessRoles(final Jwt jwt) {
        final Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ACCESS_KEY);
        if (Objects.isNull(realmAccess)) {
            return Set.of();
        }

        final Object roles = realmAccess.get(ROLES_KEY);
        if (!(roles instanceof Collection<?> collection)) {
            return Set.of();
        }

        return collection.stream()
            .map(String::valueOf)
            .filter(BUSINESS_ROLES::contains)
            .map(UserRole::valueOf)
            .collect(Collectors.toSet());
    }
}
