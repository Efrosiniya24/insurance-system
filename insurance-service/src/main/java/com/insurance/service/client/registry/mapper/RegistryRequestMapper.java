package com.insurance.service.client.registry.mapper;

import com.insurance.service.client.registry.dto.RegisterContractRequestDto;
import com.insurance.service.client.registry.dto.RegistryBeneficiaryRequestDto;
import com.insurance.service.client.registry.dto.RegistryPersonRequestDto;
import com.insurance.service.model.application.dto.ApplicationBeneficiaryResponseDto;
import com.insurance.service.model.contract.dto.ContractDto;
import com.insurance.service.model.user.dto.PersonalDataDto;
import org.mapstruct.Mapper;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Mapper(componentModel = "spring")
public interface RegistryRequestMapper {

    RegisterContractRequestDto toRequest(ContractDto contract);

    RegistryPersonRequestDto toPerson(PersonalDataDto person);

    RegistryBeneficiaryRequestDto toBeneficiary(ApplicationBeneficiaryResponseDto beneficiary);
}
