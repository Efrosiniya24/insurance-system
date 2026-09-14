package com.insurance.registry.mapper;

import com.insurance.registry.dto.RegisterContractRequestDto;
import com.insurance.registry.dto.RegisterContractResponseDto;
import com.insurance.registry.entity.RegistryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Mapper(componentModel = "spring")
public interface RegistryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contractNumber", source = "request.contractNumber")
    @Mapping(target = "contractStartDate", source = "request.contractStartDate")
    @Mapping(target = "contractEndDate", source = "request.contractEndDate")
    @Mapping(target = "sumInsured", source = "request.sumInsured")
    RegistryEntity toEntity(
        RegisterContractRequestDto request,
        String registryNumber,
        LocalDate registryDate,
        Long policyholderId,
        Long insuredUserId
    );

    RegisterContractResponseDto toResponse(RegistryEntity entity, boolean alreadyRegistered);
}
