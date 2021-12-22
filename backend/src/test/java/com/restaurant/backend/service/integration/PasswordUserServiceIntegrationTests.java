package com.restaurant.backend.service.integration;

import com.restaurant.backend.domain.Manager;
import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.service.PasswordUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static com.restaurant.backend.constants.PasswordUserServiceTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:password_user_service_integration.sql")
@Transactional
public class PasswordUserServiceIntegrationTests {
    @Autowired
    private PasswordUserService passwordUserService;

    @Test
    public void getAll_countUsers() {
        List<PasswordUser> all = passwordUserService.getAll();
        assertEquals(2, all.size());
    }

    @Test
    public void findOne_successful() {
        passwordUserService.findOne(1L);
    }

    @Test
    public void findOne_unsuccessful() {
        long id = 3L;
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> passwordUserService.findOne(id),
                "NotFoundException was expected");

        assertEquals(String.format("No user with id %d has been found", id), thrown.getMessage());
    }

    @Test
    public void create_successful() {
        Manager manager = generateManager();
        PasswordUser user = passwordUserService.create(manager);
        assertEquals("manager", user.getRole());
        List<PasswordUser> all = passwordUserService.getAll();
        assertEquals(3, all.size());
    }

    @Test
    public void create_unsuccessful_usernameUsed() {
        Manager manager = generateManager();
        manager.setUsername(USED_USERNAME);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> passwordUserService.create(manager),
                "BadRequestException was expected");

        assertEquals(String.format("User with username %s already exists.", USED_USERNAME), thrown.getMessage());
    }

    @Test
    public void update_successful() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail(USER_EMAIL);
        userDTO.setFirstName(USER_FIRST_NAME);
        userDTO.setLastName(USER_LAST_NAME);
        userDTO.setPhoneNumber(USER_PHONE_NUMBER);

        PasswordUser user = passwordUserService.update(userDTO);

        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(USER_FIRST_NAME, user.getFirstName());
        assertEquals(USER_LAST_NAME, user.getLastName());
        assertEquals(USER_PHONE_NUMBER, user.getPhoneNumber());
    }

    @Test
    public void delete_successful() {
        passwordUserService.delete(1L);
    }

    @Test
    public void delete_notFound() {
        long id = 3L;
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> passwordUserService.delete(id),
                "NotFoundException was expected");

        assertEquals(String.format("No user with id %d has been found", id), thrown.getMessage());
    }
}
