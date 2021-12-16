package com.restaurant.backend.service.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.backend.constants.StaffServiceTestConstants;
import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.domain.Waiter;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.service.StaffService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:staff_service_integration.sql")
@Transactional
public class StaffServiceIntegrationTests {
    @Autowired
    private StaffService staffService;

    @Test
    public void getAll_countSixStaffMembers() {
        List<Staff> all = staffService.getAll();
        assertEquals(6, all.size());
    }

    @Test
    public void findOne_findStaffMember_successful() {
        staffService.findOne(1L);
    }

    @Test
    public void findOne_findStaffMember_unsuccessful() {
        long id = 7L;
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> staffService.findOne(id),
                "NotFoundException was expected");

        assertEquals(String.format("No staff member with id %d has been found", id), thrown.getMessage());
    }

    @Test
    public void create_createStaff_successful() {
        Waiter waiter = generateWaiter();
        Staff staff = staffService.create(waiter);
        assertEquals("waiter", staff.getRole());
        List<Staff> all = staffService.getAll();
        assertEquals(7, all.size());
    }

    @Test
    public void create_createStaff_pinUsed() {
        Waiter waiter = generateWaiter();
        Integer pin = StaffServiceTestConstants.USED_STAFF_PIN;
        waiter.setPin(pin);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> staffService.create(waiter),
                "BadRequestException was expected");

        assertEquals(String.format("Staff member with pin %d already exists.", pin), thrown.getMessage());
    }

    @Test
    public void update_updateStaff_successful() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail(StaffServiceTestConstants.STAFF_EMAIL);
        userDTO.setFirstName(StaffServiceTestConstants.STAFF_FIRST_NAME);
        userDTO.setLastName(StaffServiceTestConstants.STAFF_LAST_NAME);
        userDTO.setPhoneNumber(StaffServiceTestConstants.STAFF_PHONE_NUMBER);

        Staff staff = staffService.update(userDTO);

        assertEquals(StaffServiceTestConstants.STAFF_EMAIL, staff.getEmail());
        assertEquals(StaffServiceTestConstants.STAFF_FIRST_NAME, staff.getFirstName());
        assertEquals(StaffServiceTestConstants.STAFF_LAST_NAME, staff.getLastName());
        assertEquals(StaffServiceTestConstants.STAFF_PHONE_NUMBER, staff.getPhoneNumber());
    }

    @Test
    public void delete_deleteStaff_successful() {
        staffService.delete(1L);
    }

    @Test
    public void delete_deleteStaff_notFound() {
        long id = 7L;
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> staffService.delete(id),
                "NotFoundException was expected");

        assertEquals(String.format("No staff member with id %d has been found", id), thrown.getMessage());
    }

    private Waiter generateWaiter() {
        Waiter waiter = new Waiter();
        waiter.setEmail(StaffServiceTestConstants.STAFF_EMAIL);
        waiter.setFirstName(StaffServiceTestConstants.STAFF_FIRST_NAME);
        waiter.setLastName(StaffServiceTestConstants.STAFF_LAST_NAME);
        waiter.setPin(StaffServiceTestConstants.STAFF_PIN);
        waiter.setMonthlyWage(StaffServiceTestConstants.STAFF_WAGE);
        waiter.setPhoneNumber(StaffServiceTestConstants.STAFF_PHONE_NUMBER);
        waiter.setDeleted(false);
        return waiter;
    }
}
