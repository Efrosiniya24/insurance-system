package com.insurance.service.service.impl;

import com.insurance.service.client.registry.RegistryClient;
import com.insurance.service.client.registry.dto.RegisterContractResponseDto;
import com.insurance.service.client.registry.mapper.RegistryRequestMapper;
import com.insurance.service.enums.ContractRegistryStatus;
import com.insurance.service.enums.SyncErrorType;
import com.insurance.service.model.contract.dto.ContractDto;
import com.insurance.service.model.sync.entity.ContractRegistrySyncEntity;
import com.insurance.service.model.sync.repository.ContractRegistrySyncRepository;
import com.insurance.service.service.ContractRegistrySyncService;
import com.insurance.service.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Service
@RequiredArgsConstructor
public class ContractRegistrySyncServiceImpl implements ContractRegistrySyncService {
    private static final int ERROR_MESSAGE_MAX_LENGTH = 255;

    private final ContractRegistrySyncRepository contractRegistrySyncRepository;
    private final ContractService contractService;
    private final RegistryClient registryClient;
    private final RegistryRequestMapper registryRequestMapper;

    @Override
    @Scheduled(fixedDelayString = "PT10S")
    public void syncPending() {
        contractRegistrySyncRepository.findAllByContractRegistryStatus(ContractRegistryStatus.PENDING)
            .forEach(this::syncOne);
    }

    private void syncOne(final ContractRegistrySyncEntity sync) {
        try {
            final ContractDto contract = contractService.getContractForRegistry(sync.getContractId());
            final RegisterContractResponseDto response = registryClient.register(
                registryRequestMapper.toRequest(contract)
            );
            markRegistered(sync, response.getRegistryNumber());
        } catch (final RestClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                markFailed(
                    sync,
                    ContractRegistryStatus.REJECTED,
                    SyncErrorType.BUSINESS,
                    ex.getStatusText()
                );
            } else {
                markFailed(
                    sync,
                    ContractRegistryStatus.PENDING,
                    SyncErrorType.TECHNICAL,
                    ex.getStatusText()
                );
            }
        } catch (ResourceAccessException exception) {
            markFailed(
                sync,
                ContractRegistryStatus.PENDING,
                SyncErrorType.TEMPORAL,
                exception.getMessage()
            );
        }
    }

    private void markRegistered(final ContractRegistrySyncEntity sync, final String registryNumber) {
        sync.setContractRegistryStatus(ContractRegistryStatus.REGISTERED);
        sync.setRegistryNumber(registryNumber);
        sync.setSyncErrorType(null);
        sync.setErrorMessage(null);
        sync.setAttemptCount(sync.getAttemptCount() + 1);
        sync.setUpdatedAt(LocalDateTime.now());
        contractRegistrySyncRepository.save(sync);
    }

    private void markFailed(
        final ContractRegistrySyncEntity sync,
        final ContractRegistryStatus status,
        final SyncErrorType errorType,
        final String errorMessage
    ) {
        sync.setContractRegistryStatus(status);
        sync.setSyncErrorType(errorType);
        sync.setErrorMessage(trimErrorMessage(errorMessage));
        sync.setAttemptCount(sync.getAttemptCount() + 1);
        sync.setUpdatedAt(LocalDateTime.now());
        contractRegistrySyncRepository.save(sync);
    }

    private String trimErrorMessage(final String errorMessage) {
        if (Objects.isNull(errorMessage) || errorMessage.length() <= ERROR_MESSAGE_MAX_LENGTH) {
            return errorMessage;
        }
        return errorMessage.substring(0, ERROR_MESSAGE_MAX_LENGTH);
    }
}
