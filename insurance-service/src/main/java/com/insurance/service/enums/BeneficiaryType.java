package com.insurance.service.enums;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 13.09.2026
 */
public enum BeneficiaryType {
    POLICYHOLDER("policyholder"),
    INSURED("insured"),
    OTHER("other");

    private final String value;

    BeneficiaryType(final String value) {
        this.value = value;
    }
}
