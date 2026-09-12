package com.insurance.service.dto;

import com.insurance.service.security.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticatedUserDto {
    private String id;
    private String username;
    private Set<UserRole> roles;
}
