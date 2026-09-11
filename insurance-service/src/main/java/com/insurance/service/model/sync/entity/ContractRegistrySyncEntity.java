package com.insurance.service.model.sync.entity;

import com.insurance.service.enums.ContractRegistryStatus;
import com.insurance.service.enums.SyncErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 10.09.2026
 */
@Entity
@Table(name = "contract_registry_sync")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractRegistrySyncEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long contractId;

    @Column(unique = true)
    private String registryNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractRegistryStatus contractRegistryStatus;

    @Enumerated(EnumType.STRING)
    private SyncErrorType syncErrorType;

    private String errorMessage;

    @Column(nullable = false)
    private int attemptCount = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
