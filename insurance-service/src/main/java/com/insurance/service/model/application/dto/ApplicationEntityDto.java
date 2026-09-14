package com.insurance.service.model.application.dto;

import com.insurance.service.enums.ApplicationStatus;
import com.insurance.service.enums.PremiumFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationEntityDto {
    private Long id;
    private String applicationNumber;
    private String createdByUserId;
    private ApplicationStatus applicationStatus;
    private Long policyholderId;
    private Long insuredId;
    private BigDecimal sumInsured;
    private BigDecimal insurancePremium;
    private PremiumFrequency premiumFrequency;
    private String rejectionReason;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
