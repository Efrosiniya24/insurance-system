package com.insurance.service.model.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 10.09.2026
 */
@Entity
@Table(name = "user_personal_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPersonalDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    private String patronymic;
    private String passportSeries;

    @Column(nullable = false)
    private String passportNumber;

    @Column(nullable = false)
    private String identificationNumber;

    @Column(nullable = false)
    private String passportIssuedBy;

    @Column(nullable = false)
    private LocalDate passportIssuedAt;

    @Column(nullable = false)
    private String registrationAddress;

    @Column(nullable = false)
    private LocalDate birthDate;

    private String gender;

    @Column(nullable = false)
    private String citizenship;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;
}
