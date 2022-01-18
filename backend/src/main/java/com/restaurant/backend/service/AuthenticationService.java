package com.restaurant.backend.service;

import com.restaurant.backend.domain.User;
import com.restaurant.backend.dto.requests.CredentialsDTO;
import com.restaurant.backend.security.TokenUtils;
import com.restaurant.backend.security.authentication.PinBasedAuthenticationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public String login(CredentialsDTO credentials) throws Unauthorized {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        return tokenUtils.generateToken(user.getUsername());
    }

    public String login(String pin) throws Unauthorized {
        Authentication authentication = authenticationManager.authenticate(new PinBasedAuthenticationToken(pin));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        return tokenUtils.generatePinToken(user.getUsername());
    }
}
