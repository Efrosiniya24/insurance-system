package com.insurance.service.model.application.specification;

import com.insurance.service.enums.ApplicationStatus;
import com.insurance.service.model.application.dto.ApplicationFilter;
import com.insurance.service.model.application.entity.ApplicationEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
public final class ApplicationSpecification {

    private ApplicationSpecification() {
    }

    public static Specification<ApplicationEntity> from(final ApplicationFilter filter) {
        if (Objects.isNull(filter)) {
            return Specification.unrestricted();
        }
        return Specification.allOf(
            hasStatus(filter.status()),
            createdFrom(filter.createdFrom()),
            createdTo(filter.createdTo())
        );
    }

    public static Specification<ApplicationEntity> createdByUserId(final String userId) {
        return (root, query, cb) -> cb.equal(root.get("createdByUserId"), userId);
    }

    private static Specification<ApplicationEntity> hasStatus(final ApplicationStatus status) {
        return (root, query, cb) -> Objects.isNull(status)
            ? null
            : cb.equal(root.get("applicationStatus"), status);
    }

    private static Specification<ApplicationEntity> createdFrom(final LocalDate createdFrom) {
        return (root, query, cb) -> Objects.isNull(createdFrom)
            ? null
            : cb.greaterThanOrEqualTo(root.get("createdAt"), createdFrom.atStartOfDay());
    }

    private static Specification<ApplicationEntity> createdTo(final LocalDate createdTo) {
        return (root, query, cb) -> Objects.isNull(createdTo)
            ? null
            : cb.lessThan(root.get("createdAt"), endOfDayExclusive(createdTo));
    }

    private static LocalDateTime endOfDayExclusive(final LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }
}
