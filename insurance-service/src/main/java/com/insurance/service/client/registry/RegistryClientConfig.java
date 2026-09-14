package com.insurance.service.client.registry;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@Configuration
@EnableConfigurationProperties(RegistryProperties.class)
public class RegistryClientConfig {

    @Bean
    public RestClient registryRestClient(final RegistryProperties properties) {
        return RestClient.builder()
            .baseUrl(properties.baseUrl())
            .defaultHeader("X-Api-Key", properties.apiKey())
            .build();
    }
}
