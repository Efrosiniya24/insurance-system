package com.insurance.service.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.insurance.service.client.registry.dto.RegisterContractRequestDto;
import com.insurance.service.client.registry.dto.RegisterContractResponseDto;
import com.insurance.service.enums.ContractRegistryStatus;
import com.insurance.service.enums.SyncErrorType;
import com.insurance.service.model.contract.dto.ContractDto;
import com.insurance.service.model.contract.repository.ContractRepository;
import com.insurance.service.model.sync.entity.ContractRegistrySyncEntity;
import com.insurance.service.model.sync.repository.ContractRegistrySyncRepository;
import com.insurance.service.service.ContractRegistrySyncService;
import com.insurance.service.service.ContractService;
import com.insurance.service.testsupport.AbstractInsuranceServiceTest;
import com.insurance.service.testsupport.ApplicationTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
class RegistrySyncTest extends AbstractInsuranceServiceTest {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRegistrySyncService contractRegistrySyncService;

    @Autowired
    private ContractRegistrySyncRepository contractRegistrySyncRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ApplicationTestData applicationTestData;

    @BeforeEach
    void setUp() {
        reset(registryClient);
        contractRegistrySyncRepository.deleteAll();
    }

    @Test
    void issue_createsPendingSyncWithoutCallingRegistry() {
        final ContractDto contract = issueApproved();

        final ContractRegistrySyncEntity sync = requireSync(contract.getId());
        assertThat(sync.getContractRegistryStatus()).isEqualTo(ContractRegistryStatus.PENDING);
        assertThat(sync.getAttemptCount()).isZero();
        assertThat(contractRepository.findById(contract.getId())).isPresent();
        verify(registryClient, never()).register(any());
    }

    @Test
    void syncPending_onSuccess_marksRegistered() {
        final ContractDto contract = issueApproved();
        stubSuccessfulRegister("GR-OK");

        contractRegistrySyncService.syncPending();

        final ContractRegistrySyncEntity sync = requireSync(contract.getId());
        assertThat(sync.getContractRegistryStatus()).isEqualTo(ContractRegistryStatus.REGISTERED);
        assertThat(sync.getRegistryNumber()).isEqualTo("GR-OK");
        assertThat(sync.getSyncErrorType()).isNull();
        assertThat(sync.getErrorMessage()).isNull();
        assertThat(sync.getAttemptCount()).isEqualTo(1);
        verify(registryClient, times(1)).register(any(RegisterContractRequestDto.class));
    }

    @Test
    void syncPending_whenRegistryUnavailable_keepsContractAndPendingTemporal() {
        final ContractDto contract = issueApproved();
        when(registryClient.register(any(RegisterContractRequestDto.class)))
            .thenThrow(new ResourceAccessException("Registry unavailable"));

        contractRegistrySyncService.syncPending();

        assertThat(contractRepository.findById(contract.getId())).isPresent();
        final ContractRegistrySyncEntity sync = requireSync(contract.getId());
        assertThat(sync.getContractRegistryStatus()).isEqualTo(ContractRegistryStatus.PENDING);
        assertThat(sync.getSyncErrorType()).isEqualTo(SyncErrorType.TEMPORAL);
        assertThat(sync.getRegistryNumber()).isNull();
        assertThat(sync.getAttemptCount()).isEqualTo(1);
    }

    @Test
    void syncPending_afterTemporaryError_retriesAndMarksRegistered() {
        final ContractDto contract = issueApproved();
        when(registryClient.register(any(RegisterContractRequestDto.class)))
            .thenThrow(new ResourceAccessException("Registry unavailable"))
            .thenReturn(successfulResponse("GR-RETRY"));

        contractRegistrySyncService.syncPending();
        ContractRegistrySyncEntity sync = requireSync(contract.getId());
        assertThat(sync.getContractRegistryStatus()).isEqualTo(ContractRegistryStatus.PENDING);
        assertThat(sync.getSyncErrorType()).isEqualTo(SyncErrorType.TEMPORAL);

        contractRegistrySyncService.syncPending();
        sync = requireSync(contract.getId());
        assertThat(sync.getContractRegistryStatus()).isEqualTo(ContractRegistryStatus.REGISTERED);
        assertThat(sync.getRegistryNumber()).isEqualTo("GR-RETRY");
        assertThat(sync.getSyncErrorType()).isNull();
        assertThat(sync.getAttemptCount()).isEqualTo(2);
        assertThat(contractRepository.findById(contract.getId())).isPresent();
        verify(registryClient, times(2)).register(any(RegisterContractRequestDto.class));
    }

    @Test
    void syncPending_doesNotRegisterAgainOnceRegistered() {
        final ContractDto contract = issueApproved();
        stubSuccessfulRegister("GR-ONCE");

        contractRegistrySyncService.syncPending();
        contractRegistrySyncService.syncPending();

        assertThat(contractRegistrySyncRepository.findAllByContractRegistryStatus(ContractRegistryStatus.REGISTERED)
            .stream()
            .filter(sync -> sync.getContractId().equals(contract.getId()))
            .count()).isEqualTo(1);
        verify(registryClient, times(1)).register(any(RegisterContractRequestDto.class));
    }

    @Test
    void syncPending_onBusinessError_marksRejectedAndDoesNotRetry() {
        final ContractDto contract = issueApproved();
        when(registryClient.register(any(RegisterContractRequestDto.class)))
            .thenThrow(HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                HttpHeaders.EMPTY,
                new byte[0],
                StandardCharsets.UTF_8
            ));

        contractRegistrySyncService.syncPending();
        contractRegistrySyncService.syncPending();

        final ContractRegistrySyncEntity sync = requireSync(contract.getId());
        assertThat(sync.getContractRegistryStatus()).isEqualTo(ContractRegistryStatus.REJECTED);
        assertThat(sync.getSyncErrorType()).isEqualTo(SyncErrorType.BUSINESS);
        assertThat(contractRepository.findById(contract.getId())).isPresent();
        verify(registryClient, times(1)).register(any(RegisterContractRequestDto.class));
    }

    private ContractDto issueApproved() {
        return contractService.issueContract(applicationTestData.createApprovedApplication(), "underwriter-test");
    }

    private ContractRegistrySyncEntity requireSync(final Long contractId) {
        return contractRegistrySyncRepository.findByContractId(contractId).orElseThrow();
    }

    private void stubSuccessfulRegister(final String registryNumber) {
        when(registryClient.register(any(RegisterContractRequestDto.class)))
            .thenReturn(successfulResponse(registryNumber));
    }

    private RegisterContractResponseDto successfulResponse(final String registryNumber) {
        return new RegisterContractResponseDto(registryNumber, LocalDate.now(), "LC-TEST", false);
    }
}
