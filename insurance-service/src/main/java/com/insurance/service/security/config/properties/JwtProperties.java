package com.insurance.service.security.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 11.09.2026
 */
@ConfigurationProperties(prefix = "app.security.jwt")
public record JwtProperties(
    String jwkSetUri,
    String issuerUri
) {
}
