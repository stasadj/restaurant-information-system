package com.restaurant.backend.controller;

import com.restaurant.backend.domain.User;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.dto.requests.CredentialsDTO;
import com.restaurant.backend.dto.responses.TokenDTO;
import com.restaurant.backend.service.AuthenticationService;
import com.restaurant.backend.support.UserMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthenticationController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody CredentialsDTO credentials) {
        LOG.debug("Received request for login");
        String token = authenticationService.login(credentials);
        LOG.debug("Authentication passed");
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
    }

    @PostMapping("/pin-login")
    public ResponseEntity<TokenDTO> pinLogin(@RequestBody String pin) {
        LOG.debug("Received request for login");
        String token = authenticationService.login(pin);
        LOG.debug("Authentication passed");
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public UserDTO whoami(@AuthenticationPrincipal User user) {
        return userMapper.convert(user);
    }
}
