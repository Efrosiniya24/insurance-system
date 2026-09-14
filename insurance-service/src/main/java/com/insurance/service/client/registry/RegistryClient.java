package com.insurance.service.client.registry;

import com.insurance.service.client.registry.dto.RegisterContractRequestDto;
import com.insurance.service.client.registry.dto.RegisterContractResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Component
@RequiredArgsConstructor
public class RegistryClient {
    private final RestClient registryRestClient;

    public RegisterContractResponseDto register(final RegisterContractRequestDto request) {
        return registryRestClient.post()
            .uri("/registry/contracts")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(RegisterContractResponseDto.class);
    }
}
