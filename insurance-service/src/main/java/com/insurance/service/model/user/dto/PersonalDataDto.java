package com.insurance.service.model.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDataDto {
    private Long id;
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private String patronymic;
    private String passportSeries;

    @NotBlank
    private String passportNumber;

    @NotBlank
    private String identificationNumber;

    @NotBlank
    private String passportIssuedBy;

    @NotNull
    @PastOrPresent
    private LocalDate passportIssuedAt;

    @NotBlank
    private String registrationAddress;

    @NotNull
    @Past
    private LocalDate birthDate;

    private String gender;

    @NotBlank
    private String citizenship;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;
}
