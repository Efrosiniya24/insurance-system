package com.insurance.service.enums;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 10.09.2026
 */
public enum InsuranceEvent {
    RETIREMENT_AGE_ATTAINMENT("retirement age attainment"),
    SPECIFIC_AGE_ATTAINMENT("specific_age_attainment"),
    DEATH("death"),
    ACCIDENTAL_DEATH("accidental_death"),
    DISABILITY("disability"),
    ACCIDENTAL_DISABILITY("accidental_disability"),
    BODILY_INJURY("bodily_injury"),
    TEMPORARY_DISABILITY("temporary_disability"),
    TEMPORARY_ACCIDENTAL_DISABILITY("temporary_accidental_disability"),
    CRITICAL_ILLNESS("critical_illness");

    private final String value;

    InsuranceEvent(final String value) {
        this.value = value;
    }
}
