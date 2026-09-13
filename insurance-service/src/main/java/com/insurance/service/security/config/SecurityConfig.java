package com.insurance.service.security.config;

import com.insurance.service.security.authentication.JwtUserAuthentication;
import com.insurance.service.security.config.properties.JwtProperties;
import com.insurance.service.security.dto.AuthenticatedUserDto;
import com.insurance.service.security.exception.SecurityExceptionHandler;
import com.insurance.service.security.service.UserSecurityService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {
    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        final HttpSecurity http,
        final Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter,
        final SecurityExceptionHandler securityExceptionHandler
    ) {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                .authenticationEntryPoint(securityExceptionHandler)
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(securityExceptionHandler)
                .accessDeniedHandler(securityExceptionHandler)
            )
            .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(final JwtProperties properties) {
        final NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(properties.jwkSetUri()).build();
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(properties.issuerUri()));
        return decoder;
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter(
        final UserSecurityService userSecurityService
    ) {
        return jwt -> {
            final AuthenticatedUserDto user = userSecurityService.buildAuthenticatedUser(jwt);
            final Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getValue()))
                .toList();
            return new JwtUserAuthentication(user, jwt, authorities);
        };
    }
}