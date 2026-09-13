package com.insurance.service.model.application.mapper;

import com.insurance.service.model.application.dto.ApplicationInsuranceEventDto;
import com.insurance.service.model.application.entity.ApplicationInsuranceEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Mapper(componentModel = "spring")
public interface ApplicationInsuranceEventMapper {

    @Mapping(target = "id", ignore = true)
    ApplicationInsuranceEventEntity toEntity(ApplicationInsuranceEventDto dto);

    ApplicationInsuranceEventDto toDto(ApplicationInsuranceEventEntity entity);

    List<ApplicationInsuranceEventEntity> toEntityList(List<ApplicationInsuranceEventDto> dtos);

    List<ApplicationInsuranceEventDto> toDtoList(List<ApplicationInsuranceEventEntity> entities);
}
