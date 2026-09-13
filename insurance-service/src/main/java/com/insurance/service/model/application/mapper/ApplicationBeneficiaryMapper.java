package com.insurance.service.model.application.mapper;

import com.insurance.service.model.application.dto.ApplicationBeneficiaryDto;
import com.insurance.service.model.application.entity.ApplicationBeneficiaryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Mapper(componentModel = "spring")
public interface ApplicationBeneficiaryMapper {

    @Mapping(target = "id", ignore = true)
    ApplicationBeneficiaryEntity toEntity(ApplicationBeneficiaryDto dto);

    ApplicationBeneficiaryDto toDto(ApplicationBeneficiaryEntity entity);

    List<ApplicationBeneficiaryEntity> toEntityList(List<ApplicationBeneficiaryDto> dtos);

    List<ApplicationBeneficiaryDto> toDtoList(List<ApplicationBeneficiaryEntity> entities);
}
