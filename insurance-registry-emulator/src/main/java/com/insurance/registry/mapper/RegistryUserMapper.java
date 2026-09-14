package com.insurance.registry.mapper;

import com.insurance.registry.dto.RegistryPersonDataDto;
import com.insurance.registry.entity.RegistryUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Mapper(componentModel = "spring")
public interface RegistryUserMapper {

    @Mapping(target = "id", ignore = true)
    RegistryUserEntity toEntity(RegistryPersonDataDto dto);
}
