package com.restaurant.backend.security.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.backend.security.TokenUtils;
import com.restaurant.backend.service.JWTUserDetailsService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    private JWTUserDetailsService userDetailsService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username;
        String authToken = tokenUtils.getToken(request);

        if (authToken != null) {
            username = tokenUtils.getUsernameFromToken(authToken);

            if (username != null) {
                UserDetails userDetails = null;
                String userType = tokenUtils.getUserTypeFromToken(authToken);
                switch (userType) {
                case TokenUtils.USER_TYPE_PASSWORD:
                    userDetails = userDetailsService.loadUserByUsername(username);
                    break;
                case TokenUtils.USER_TYPE_PIN:
                    try {
                        int pin = Integer.parseInt(username);
                        userDetails = userDetailsService.loadUserByPin(pin);
                    } catch (NumberFormatException e) {
                    }
                    break;
                }

                if (userDetails != null && tokenUtils.validateToken(authToken, userDetails)) {
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
