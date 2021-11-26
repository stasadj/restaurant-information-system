package com.restaurant.backend.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.backend.domain.User;
import com.restaurant.backend.dto.requests.CredentialsDTO;
import com.restaurant.backend.security.TokenUtils;
import com.restaurant.backend.security.authentication.PinBasedAuthenticationToken;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public void login(CredentialsDTO credentials, HttpServletResponse response) throws Unauthorized {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.addCookie(generateJWTCookieForUser(authentication));
    }

    public void login(String pin, HttpServletResponse response) throws Unauthorized {
        Authentication authentication = authenticationManager.authenticate(new PinBasedAuthenticationToken(pin));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.addCookie(generateJWTCookieForStaff(authentication));
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    private Cookie generateCookie(String jwt) {
        Cookie cookie = new Cookie("accessToken", jwt);
        cookie.setMaxAge(7 * 24 * 60 * 60); // Expires in 7 days
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // Global cookie accessible everywhere

        return cookie;
    }

    private Cookie generateJWTCookieForUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        return generateCookie(jwt);
    }

    private Cookie generateJWTCookieForStaff(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generatePinToken(user.getUsername());
        return generateCookie(jwt);
    }
}
