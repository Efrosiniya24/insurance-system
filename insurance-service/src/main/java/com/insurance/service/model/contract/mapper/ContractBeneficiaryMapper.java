package com.insurance.service.model.contract.mapper;

import com.insurance.service.model.contract.dto.ContractBeneficiaryDto;
import com.insurance.service.model.contract.entity.ContractBeneficiaryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Mapper(componentModel = "spring")
public interface ContractBeneficiaryMapper {

    @Mapping(target = "id", ignore = true)
    ContractBeneficiaryEntity toEntity(ContractBeneficiaryDto dto);

    ContractBeneficiaryDto toDto(ContractBeneficiaryEntity entity);

    List<ContractBeneficiaryEntity> toEntityList(List<ContractBeneficiaryDto> dtos);

    List<ContractBeneficiaryDto> toDtoList(List<ContractBeneficiaryEntity> entities);
}
