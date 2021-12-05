package com.restaurant.backend.security.authentication.provider;

import java.util.Optional;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.repository.StaffRepository;
import com.restaurant.backend.security.authentication.PinBasedAuthenticationToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PinUserAuthenticationProvider implements AuthenticationProvider {
    private StaffRepository staffRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String pin;
        if (authentication == null) {
            return null;
        }
        if (authentication.getCredentials() != null) {
            pin = authentication.getCredentials().toString();
        } else {
            pin = authentication.getName();
        }

        try {
            Optional<Staff> maybeUser = staffRepository.findByPin(Integer.parseInt(pin));
            if (maybeUser.isPresent()) {
                Staff user = maybeUser.get();
                return new PinBasedAuthenticationToken(user.getAuthorities(), user);
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PinBasedAuthenticationToken.class);
    }

}
