package com.insurance.service.model.application.entity;

import com.insurance.service.enums.InsuranceEvent;
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
@Table(name = "application_insurance_event")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationInsuranceEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long applicationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsuranceEvent insuranceEvent;
}
