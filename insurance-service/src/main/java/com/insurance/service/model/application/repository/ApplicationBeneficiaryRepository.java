package com.insurance.service.model.application.repository;

import com.insurance.service.model.application.entity.ApplicationBeneficiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
@Repository
public interface ApplicationBeneficiaryRepository extends JpaRepository<ApplicationBeneficiaryEntity, Long> {
}
