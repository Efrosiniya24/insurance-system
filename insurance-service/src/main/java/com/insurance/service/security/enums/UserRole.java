package com.insurance.service.security.enums;

import lombok.Getter;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
@Getter
public enum UserRole {
    POLICYHOLDER("POLICYHOLDER"),
    UNDERWRITER("UNDERWRITER");

    private final String value;

    UserRole(final String value) {
        this.value = value;
    }
}
