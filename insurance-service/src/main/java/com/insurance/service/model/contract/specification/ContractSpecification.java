package com.insurance.service.model.contract.specification;

import com.insurance.service.enums.ContractRegistryStatus;
import com.insurance.service.model.contract.dto.ContractFilter;
import com.insurance.service.model.contract.entity.ContractEntity;
import com.insurance.service.model.sync.entity.ContractRegistrySyncEntity;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
public final class ContractSpecification {
    private ContractSpecification() {
    }

    public static Specification<ContractEntity> from(final ContractFilter filter) {
        if (Objects.isNull(filter)) {
            return Specification.unrestricted();
        }
        return Specification.allOf(
            hasRegistryStatus(filter.registryStatus()),
            createdFrom(filter.createdFrom()),
            createdTo(filter.createdTo()),
            hasContractStartDate(filter.contractStartDate()),
            hasContractEndDate(filter.contractEndDate())
        );
    }

    public static Specification<ContractEntity> applicationIdIn(final Collection<Long> applicationIds) {
        return (root, query, cb) -> root.get("applicationId").in(applicationIds);
    }

    private static Specification<ContractEntity> hasRegistryStatus(final ContractRegistryStatus registryStatus) {
        return (root, query, cb) -> {
            if (Objects.isNull(registryStatus)) {
                return null;
            }
            final Subquery<Integer> subquery = query.subquery(Integer.class);
            final Root<ContractRegistrySyncEntity> sync = subquery.from(ContractRegistrySyncEntity.class);
            subquery.select(cb.literal(1))
                .where(
                    cb.equal(sync.get("contractId"), root.get("id")),
                    cb.equal(sync.get("contractRegistryStatus"), registryStatus)
                );
            return cb.exists(subquery);
        };
    }

    private static Specification<ContractEntity> createdFrom(final LocalDate createdFrom) {
        return (root, query, cb) -> Objects.isNull(createdFrom)
            ? null
            : cb.greaterThanOrEqualTo(root.get("createdAt"), createdFrom.atStartOfDay());
    }

    private static Specification<ContractEntity> createdTo(final LocalDate createdTo) {
        return (root, query, cb) -> Objects.isNull(createdTo)
            ? null
            : cb.lessThan(root.get("createdAt"), endOfDayExclusive(createdTo));
    }

    private static Specification<ContractEntity> hasContractStartDate(final LocalDate contractStartDate) {
        return (root, query, cb) -> Objects.isNull(contractStartDate)
            ? null
            : cb.equal(root.get("contractStartDate"), contractStartDate);
    }

    private static Specification<ContractEntity> hasContractEndDate(final LocalDate contractEndDate) {
        return (root, query, cb) -> Objects.isNull(contractEndDate)
            ? null
            : cb.equal(root.get("contractEndDate"), contractEndDate);
    }

    private static LocalDateTime endOfDayExclusive(final LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }
}
