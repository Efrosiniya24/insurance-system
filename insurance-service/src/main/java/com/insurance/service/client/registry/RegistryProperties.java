package com.insurance.service.client.registry;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 15.09.2026
 */
@ConfigurationProperties(prefix = "app.registry")
public record RegistryProperties(
    String baseUrl,
    String apiKey
) {
}
