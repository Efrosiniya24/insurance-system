package com.insurance.service.model.user.mapper;

import com.insurance.service.model.user.dto.PersonalDataDto;
import com.insurance.service.model.user.entity.UserPersonalDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Mapper(componentModel = "spring")
public interface UserPersonalDataMapper {

    @Mapping(target = "id", ignore = true)
    UserPersonalDataEntity toEntity(PersonalDataDto dto);

    PersonalDataDto toDto(UserPersonalDataEntity entity);

    List<UserPersonalDataEntity> toEntityList(List<PersonalDataDto> dtos);

    List<PersonalDataDto> toDtoList(List<UserPersonalDataEntity> entities);
}
