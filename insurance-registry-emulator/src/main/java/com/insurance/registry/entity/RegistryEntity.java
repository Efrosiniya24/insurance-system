package com.insurance.registry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 10.09.2026
 */
@Entity
@Table(name = "registry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String registryNumber;

    @Column(nullable = false)
    private LocalDate registryDate;

    @Column(unique = true, nullable = false)
    private String contractNumber;

    @Column(nullable = false)
    private LocalDate contractStartDate;

    @Column(nullable = false)
    private LocalDate contractEndDate;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal sumInsured;

    @Column(nullable = false)
    private Long insuredUserId;

    @Column(nullable = false)
    private Long policyholderId;
}
