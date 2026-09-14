package com.insurance.registry.entity;

import com.insurance.registry.enums.BeneficiaryType;
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

/**
 * @author yefrosiniya.zinkovskaya
 * @since 10.09.2026
 */
@Entity
@Table(name = "contract_beneficiary")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractBeneficiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long registryId;

    @Column(nullable = false)
    private Long beneficiaryId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BeneficiaryType beneficiaryType;
}
