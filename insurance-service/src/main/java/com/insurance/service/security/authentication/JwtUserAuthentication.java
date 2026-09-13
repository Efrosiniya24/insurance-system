package com.insurance.service.security.authentication;

import com.insurance.service.security.dto.AuthenticatedUserDto;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

/**
 * @author yefrosiniya.zinkovskaya
 * @since 12.09.2026
 */
public class JwtUserAuthentication extends AbstractAuthenticationToken {
    private final AuthenticatedUserDto principal;
    private final Jwt jwt;

    public JwtUserAuthentication(
        final AuthenticatedUserDto principal,
        final Jwt jwt,
        final Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.principal = principal;
        this.jwt = jwt;
        setAuthenticated(true);
    }

    @Override
    public AuthenticatedUserDto getPrincipal() {
        return principal;
    }

    @Override
    public Jwt getCredentials() {
        return jwt;
    }

    @Override
    public String getName() {
        return principal.getId();
    }
}
