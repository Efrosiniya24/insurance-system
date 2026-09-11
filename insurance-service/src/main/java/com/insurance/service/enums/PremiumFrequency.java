package com.insurance.service.enums;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 10.09.2026
 */
public enum PremiumFrequency {
    ONCE("once"),
    MONTHLY("monthly"),
    YEARLY("yearly");

    private final String value;

    PremiumFrequency(final String value) {
        this.value = value;
    }
}
