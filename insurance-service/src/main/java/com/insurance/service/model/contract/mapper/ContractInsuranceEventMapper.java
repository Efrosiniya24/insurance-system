package com.insurance.service.model.contract.mapper;

import com.insurance.service.model.contract.dto.ContractInsuranceEventDto;
import com.insurance.service.model.contract.entity.ContractInsuranceEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Mapper(componentModel = "spring")
public interface ContractInsuranceEventMapper {

    @Mapping(target = "id", ignore = true)
    ContractInsuranceEventEntity toEntity(ContractInsuranceEventDto dto);

    ContractInsuranceEventDto toDto(ContractInsuranceEventEntity entity);

    List<ContractInsuranceEventEntity> toEntityList(List<ContractInsuranceEventDto> dtos);

    List<ContractInsuranceEventDto> toDtoList(List<ContractInsuranceEventEntity> entities);
}
