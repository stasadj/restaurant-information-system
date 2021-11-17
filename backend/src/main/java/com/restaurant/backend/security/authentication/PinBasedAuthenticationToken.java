package com.restaurant.backend.security.authentication;

import java.util.Arrays;
import java.util.Collection;

import com.restaurant.backend.domain.User;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

public class PinBasedAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private User authenticatedUser;
    private @Getter String pin;

    public PinBasedAuthenticationToken(String pin) {
        super(Arrays.asList());
        this.pin = pin;
    }

    public PinBasedAuthenticationToken(Collection<? extends GrantedAuthority> authorities, User authenticatedUser) {
        super(authorities);
        this.authenticatedUser = authenticatedUser;
    }

    @Override
    public String getName() {
        return pin;
    }

    @Override
    public Object getCredentials() {
        return authenticatedUser.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }

}
