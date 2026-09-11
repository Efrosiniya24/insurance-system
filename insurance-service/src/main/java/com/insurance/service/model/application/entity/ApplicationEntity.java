package com.insurance.service.model.application.entity;

import com.insurance.service.enums.ApplicationStatus;
import com.insurance.service.enums.PremiumFrequency;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 10.09.2026
 */
@Entity
@Table(name = "application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String applicationNumber;

    @Column(nullable = false)
    private String createdByUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    @Column(nullable = false)
    private Long policyholderId;

    @Column(nullable = false)
    private Long insuredId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal sumInsured;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal insurancePremium;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PremiumFrequency premiumFrequency;

    private String rejectionReason;

    @Column(nullable = false)
    private LocalDate contractStartDate;

    @Column(nullable = false)
    private LocalDate contractEndDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
