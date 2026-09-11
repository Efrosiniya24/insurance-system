package com.insurance.service.model.application.repository;

import com.insurance.service.model.application.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
}
