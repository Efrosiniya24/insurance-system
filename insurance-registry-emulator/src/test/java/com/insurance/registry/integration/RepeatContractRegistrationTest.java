package com.insurance.registry.integration;

import static org.assertj.core.api.Assertions.assertThat;
import com.insurance.registry.dto.RegisterContractRequestDto;
import com.insurance.registry.dto.RegisterContractResponseDto;
import com.insurance.registry.dto.RegistryPersonDataDto;
import com.insurance.registry.enums.EmulatorMode;
import com.insurance.registry.repository.RegistryRepository;
import com.insurance.registry.service.EmulatorModeService;
import com.insurance.registry.service.RegistryService;
import com.insurance.registry.testsupport.AbstractRegistryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
class RepeatContractRegistrationTest extends AbstractRegistryTest {

    @Autowired
    private RegistryService registryService;

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    private EmulatorModeService emulatorModeService;

    @BeforeEach
    void successMode() {
        emulatorModeService.setMode(EmulatorMode.SUCCESS);
    }

    @Test
    void register_sameContractTwice_createsOnlyOneRegistryRecord() {
        final String contractNumber = "LC-" + UUID.randomUUID().toString().substring(0, 8);
        final RegisterContractRequestDto request = request(contractNumber);

        final RegisterContractResponseDto first = registryService.register(request);
        final RegisterContractResponseDto second = registryService.register(request);

        assertThat(first.isAlreadyRegistered()).isFalse();
        assertThat(second.isAlreadyRegistered()).isTrue();
        assertThat(second.getRegistryNumber()).isEqualTo(first.getRegistryNumber());
        assertThat(second.getContractNumber()).isEqualTo(contractNumber);
        assertThat(registryRepository.findAll())
            .filteredOn(entity -> contractNumber.equals(entity.getContractNumber()))
            .hasSize(1);
    }

    private RegisterContractRequestDto request(final String contractNumber) {
        final RegistryPersonDataDto person = new RegistryPersonDataDto(
            "Anna",
            "Ivanova",
            null,
            "1234567A001PB1"
        );
        return new RegisterContractRequestDto(
            contractNumber,
            LocalDate.of(2026, 10, 1),
            LocalDate.of(2027, 10, 1),
            new BigDecimal("100000.00"),
            person,
            person,
            List.of(),
            Set.of()
        );
    }
}
