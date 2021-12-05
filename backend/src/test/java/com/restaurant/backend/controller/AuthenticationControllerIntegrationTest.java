package com.restaurant.backend.controller;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.backend.dto.requests.CredentialsDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static com.restaurant.backend.constants.AuthenticationServiceTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:auth_service_integration.sql")
@Transactional
public class AuthenticationControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @ParameterizedTest
    @MethodSource("passwordCredentials")
    public void login_passwordUser_parameterized(CredentialsDTO credentials, Boolean expectedToLogin) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        if (expectedToLogin) {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(credentials)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            checkForCookie(expectedToLogin, response);
        } else {
            NestedServletException thrown = assertThrows(NestedServletException.class, () -> {
                mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials)))
                        .andExpect(res -> assertTrue(res.getResolvedException() instanceof NestedServletException))
                        .andReturn();
            }, "NestedServletException expected");
            assertTrue(thrown.getCause() instanceof BadCredentialsException, "BadCredentialsException expected");
        }
    }

    @ParameterizedTest
    @MethodSource("pinCredentials")
    public void login_pinUser_successful(String pin, Boolean expectedToLogin) throws Exception {
        if (expectedToLogin) {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/pin-login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(pin))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            checkForCookie(expectedToLogin, response);
        } else {
            NestedServletException thrown = assertThrows(NestedServletException.class, () -> {
                mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/pin-login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pin))
                        .andExpect(res -> assertTrue(res.getResolvedException() instanceof NestedServletException))
                        .andReturn();
            }, "NestedServletException expected");
            assertTrue(thrown.getCause() instanceof ProviderNotFoundException, "ProviderNotFoundException expected");
        }
    }

    private void checkForCookie(Boolean expectedToLogin, MockHttpServletResponse response) {
        // If we have a cookie, we've successfully logged in.
        if (expectedToLogin) {
            assertNotNull(response.getCookie("accessToken"));
        } else {
            assertNull(response.getCookie("accessToken"));
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
