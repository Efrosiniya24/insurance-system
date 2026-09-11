package com.insurance.service.model.user.repository;

import com.insurance.service.model.user.entity.UserPersonalDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface UserPersonalDataRepository extends JpaRepository<UserPersonalDataEntity, Long> {
}
