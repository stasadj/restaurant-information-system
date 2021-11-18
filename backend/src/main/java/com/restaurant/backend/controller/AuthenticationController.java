package com.restaurant.backend.controller;

import javax.servlet.http.HttpServletResponse;

import com.restaurant.backend.domain.User;
import com.restaurant.backend.dto.CredentialsDTO;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.service.AuthenticationService;
import com.restaurant.backend.support.UserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthenticationController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationService authenticationService;

    private UserMapper userMapper;

    @PostMapping("/login")
    public void login(@RequestBody CredentialsDTO credentials, HttpServletResponse response) {
        LOG.debug("Received request for login");
        authenticationService.login(credentials, response);
        LOG.debug("Authentication passed");
    }

    @PostMapping("/pin-login")
    public void pinLogin(@RequestBody String pin, HttpServletResponse response) {
        LOG.debug("Received request for login");
        authenticationService.login(pin, response);
        LOG.debug("Authentication passed");
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authenticationService.logout(response);
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public UserDTO whoami(@AuthenticationPrincipal User user) {
        return userMapper.convertToDto(user);
    }
}
