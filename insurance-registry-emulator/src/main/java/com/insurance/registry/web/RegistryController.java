package com.insurance.registry.web;

import com.insurance.registry.dto.RegisterContractRequestDto;
import com.insurance.registry.dto.RegisterContractResponseDto;
import com.insurance.registry.service.RegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 14.09.2026
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/registry")
public class RegistryController {
    private final RegistryService registryService;

    @PostMapping("/contracts")
    public ResponseEntity<RegisterContractResponseDto> register(
        final @RequestBody RegisterContractRequestDto request
    ) {
        final RegisterContractResponseDto response = registryService.register(request);
        return ResponseEntity
            .status(response.isAlreadyRegistered() ? HttpStatus.OK : HttpStatus.CREATED)
            .body(response);
    }
}