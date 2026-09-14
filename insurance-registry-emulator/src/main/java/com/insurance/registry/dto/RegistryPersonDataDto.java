package com.insurance.registry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryPersonDataDto {
    private String name;
    private String surname;
    private String patronymic;
    private String identificationNumber;
}