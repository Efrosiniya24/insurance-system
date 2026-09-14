package com.insurance.service.client.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryPersonRequestDto {
    private String name;
    private String surname;
    private String patronymic;
    private String identificationNumber;
}
