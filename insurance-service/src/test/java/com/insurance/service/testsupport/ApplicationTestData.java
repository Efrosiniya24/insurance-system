package com.insurance.service.testsupport;

import com.insurance.service.enums.ApplicationStatus;
import com.insurance.service.enums.PremiumFrequency;
import com.insurance.service.model.application.entity.ApplicationEntity;
import com.insurance.service.model.application.repository.ApplicationRepository;
import com.insurance.service.model.user.entity.UserPersonalDataEntity;
import com.insurance.service.model.user.repository.UserPersonalDataRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@RequiredArgsConstructor
public class ApplicationTestData {
    private final UserPersonalDataRepository userPersonalDataRepository;
    private final ApplicationRepository applicationRepository;

    public Long createApprovedApplication() {
        return createApplication(ApplicationStatus.APPROVED);
    }

    public Long createApplication(final ApplicationStatus status) {
        final UserPersonalDataEntity person = userPersonalDataRepository.save(
            UserPersonalDataEntity.builder()
                .name("Anna")
                .surname("Ivanova")
                .passportNumber("1234567")
                .identificationNumber("1234567A001PB1")
                .passportIssuedBy("RUVS Minsk")
                .passportIssuedAt(LocalDate.of(2018, 5, 10))
                .registrationAddress("Minsk")
                .birthDate(LocalDate.of(1990, 3, 15))
                .citizenship("BY")
                .phoneNumber("+375291112233")
                .email("anna@example.com")
                .build()
        );
        final LocalDateTime now = LocalDateTime.now();
        final ApplicationEntity application = applicationRepository.save(
            ApplicationEntity.builder()
                .applicationNumber("EP-" + UUID.randomUUID().toString().substring(0, 8))
                .createdByUserId("policyholder-test")
                .applicationStatus(status)
                .policyholderId(person.getId())
                .insuredId(person.getId())
                .sumInsured(new BigDecimal("100000.00"))
                .insurancePremium(new BigDecimal("1200.00"))
                .premiumFrequency(PremiumFrequency.YEARLY)
                .contractStartDate(LocalDate.of(2026, 10, 1))
                .contractEndDate(LocalDate.of(2027, 10, 1))
                .createdAt(now)
                .updatedAt(now)
                .build()
        );
        return application.getId();
    }
}
