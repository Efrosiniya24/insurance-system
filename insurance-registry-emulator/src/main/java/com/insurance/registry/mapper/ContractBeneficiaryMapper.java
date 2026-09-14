package com.insurance.registry.mapper;

import com.insurance.registry.dto.ContractBeneficiaryDto;
import com.insurance.registry.entity.ContractBeneficiaryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Mapper(componentModel = "spring")
public interface ContractBeneficiaryMapper {

    @Mapping(target = "id", ignore = true)
    ContractBeneficiaryEntity toEntity(ContractBeneficiaryDto dto);

    List<ContractBeneficiaryEntity> toEntityList(List<ContractBeneficiaryDto> dtos);
}
