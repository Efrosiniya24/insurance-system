package com.insurance.service.model.application.repository;

import com.insurance.service.model.application.entity.ApplicationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    List<ApplicationEntity> findAllByCreatedByUserId(String createdByUserId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM ApplicationEntity a WHERE a.id = :id")
    Optional<ApplicationEntity> findByIdForContract(Long id);

    @Query("select createdByUserId from ApplicationEntity where id = :applicationId")
    Optional<String> findCreatedByUserId(Long applicationId);

    @Query("select id from ApplicationEntity where createdByUserId = :createdByUserId")
    List<Long> findAllIdsByCreatedByUserId(String createdByUserId);
}
