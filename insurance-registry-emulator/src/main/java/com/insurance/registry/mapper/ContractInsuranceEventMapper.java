package com.insurance.registry.mapper;

import com.insurance.registry.dto.ContractInsuranceEventDto;
import com.insurance.registry.entity.ContractInsuranceEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Mapper(componentModel = "spring")
public interface ContractInsuranceEventMapper {

    @Mapping(target = "id", ignore = true)
    ContractInsuranceEventEntity toEntity(ContractInsuranceEventDto dto);

    List<ContractInsuranceEventEntity> toEntityList(List<ContractInsuranceEventDto> dtos);
}
