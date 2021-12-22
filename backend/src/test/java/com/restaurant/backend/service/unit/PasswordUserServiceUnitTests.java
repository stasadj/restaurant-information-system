package com.restaurant.backend.service.unit;

import static com.restaurant.backend.constants.PasswordUserServiceTestConstants.*;
import com.restaurant.backend.domain.Manager;
import com.restaurant.backend.dto.requests.ChangeUsernameDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.PasswordUserRepository;
import com.restaurant.backend.service.PasswordUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Transactional
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class PasswordUserServiceUnitTests {
    @MockBean
    private PasswordUserRepository passwordUserRepository;

    @Autowired
    private PasswordUserService passwordUserService;

    @Test
    public void create_saveUser_successful() {
        Manager manager = generateManager();
        passwordUserService.create(manager);
    }

    @Test
    public void create_saveUser_conflictingUsername() {
        Manager manager = generateManager();

        when(passwordUserRepository.findByUsername(anyString())).thenReturn(Optional.of(manager));

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> passwordUserService.create(manager),
                "BadRequestException was expected");

        assertEquals(String.format("User with username %s already exists.", manager.getUsername()), thrown.getMessage());
    }

    @Test
    public void changePin_changeStaffPin_successful() {
        Manager manager = generateManager();
        when(passwordUserRepository.findByUsername(eq(USERNAME)))
                .thenReturn(Optional.of(manager));

        ChangeUsernameDTO dto = new ChangeUsernameDTO();
        dto.setCurrentUsername(USERNAME);
        dto.setNewUsername(OTHER_USERNAME);

        passwordUserService.changeUsername(dto);
    }

    @Test
    public void changePin_changeStaffPin_pinTaken() {
        Manager manager = generateManager();
        when(passwordUserRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(manager));

        ChangeUsernameDTO dto = new ChangeUsernameDTO();
        dto.setCurrentUsername(USERNAME);
        dto.setNewUsername(OTHER_USERNAME);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> passwordUserService.changeUsername(dto),
                "BadRequestException was expected");

        assertEquals("New username is already in use.", thrown.getMessage());
    }

    @Test
    public void changePin_changeStaffPin_nonexistentPin() {
        ChangeUsernameDTO dto = new ChangeUsernameDTO();
        dto.setCurrentUsername(USERNAME);
        dto.setNewUsername(OTHER_USERNAME);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> passwordUserService.changeUsername(dto),
                "NotFoundException was expected");

        assertEquals("Current username does not exist.", thrown.getMessage());
    }
}
