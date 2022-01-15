package com.restaurant.backend.service.integration;

import com.restaurant.backend.dto.requests.CredentialsDTO;
import com.restaurant.backend.service.AuthenticationService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

import static com.restaurant.backend.constants.AuthenticationServiceTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:auth_service_integration.sql")
@Transactional
public class AuthenticationServiceIntegrationTests {
    @Autowired
    private AuthenticationService authenticationService;

    @ParameterizedTest
    @MethodSource("passwordCredentials")
    public void login_passwordUser_parameterized(CredentialsDTO credentials, Boolean expectedToLogin) {
        if (expectedToLogin) {
            String token = authenticationService.login(credentials);
        } else {
            assertThrows(BadCredentialsException.class, () -> {
                String token = authenticationService.login(credentials);
            });
        }
    }

    @ParameterizedTest
    @MethodSource("pinCredentials")
    public void login_pinUser_parameterized(String pin, Boolean expectedToLogin) {
        if (expectedToLogin) {
            String token = authenticationService.login(pin);
        } else {
            assertThrows(ProviderNotFoundException.class, () -> {
                String token = authenticationService.login(pin);
            });
        }
    }

    /// Returns a collection of [Pin, Expected To Login]
    private static Collection<Object[]> pinCredentials() {
        return Arrays.asList(new Object[][] {
                { VALID_PIN_1, true },
                { VALID_PIN_2, true },
                { VALID_PIN_3, true },
                { VALID_PIN_4, true },
                { VALID_PIN_5, true },
                { VALID_PIN_6, true },
                { INVALID_PIN_1, false },
                { INVALID_PIN_2, false },
                { INVALID_PIN_3, false },
                { INVALID_PIN_4, false },
                { INVALID_PIN_5, false },
                { INVALID_PIN_6, false }
        });
    }

    /// Returns a collection of [CredentialsDTO, Expected To Login]
    private static Collection<Object[]> passwordCredentials() {
        return Arrays.asList(new Object[][] {
                { new CredentialsDTO(ADMIN_USERNAME, USER_PASSWORD), true },
                { new CredentialsDTO(MANAGER_USERNAME, USER_PASSWORD), true },
                { new CredentialsDTO(INVALID_USERNAME_1, INVALID_PASSWORD_1), false },
                { new CredentialsDTO(INVALID_USERNAME_2, INVALID_PASSWORD_2), false },
                { new CredentialsDTO(INVALID_USERNAME_3, INVALID_PASSWORD_3), false },
                { new CredentialsDTO(INVALID_USERNAME_4, INVALID_PASSWORD_4), false },
                { new CredentialsDTO(INVALID_USERNAME_5, INVALID_PASSWORD_5), false }
        });
    }
}
