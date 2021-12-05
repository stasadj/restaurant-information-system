package com.restaurant.backend.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import com.restaurant.backend.constants.StaffServiceTestConstants;
import com.restaurant.backend.domain.Waiter;
import com.restaurant.backend.dto.requests.ChangePinDTO;
import com.restaurant.backend.dto.requests.SetSalaryDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.StaffRepository;
import com.restaurant.backend.service.StaffService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@Transactional
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class StaffServiceUnitTests {
    @MockBean
    private StaffRepository staffRepository;

    @Autowired
    private StaffService staffService;

    @Test
    public void create_saveStaffMember_successful() {
        Waiter staff = generateWaiter();
        staffService.create(staff);
    }

    @Test
    public void create_saveStaffMember_conflictingPin() {
        Waiter staff = generateWaiter();

        // No pin is valid anymore
        when(staffRepository.findByPin(anyInt())).thenReturn(Optional.of(staff));

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            staffService.create(staff);
        }, "BadRequestException was expected");

        assertEquals(String.format("Staff member with pin %d already exists.", staff.getPin()), thrown.getMessage());
    }

    @Test
    public void changePin_changeStaffPin_successful() {
        Waiter staff = generateWaiter();
        when(staffRepository.findByPin(eq(StaffServiceTestConstants.STAFF_PIN)))
                .thenReturn(Optional.of(staff));

        ChangePinDTO dto = new ChangePinDTO();
        dto.setCurrentPin(StaffServiceTestConstants.STAFF_PIN);
        dto.setNewPin(StaffServiceTestConstants.OTHER_PIN);

        staffService.changePin(dto);
    }

    @Test
    public void changePin_changeStaffPin_pinTaken() {
        Waiter staff = generateWaiter();
        Integer staffPin = StaffServiceTestConstants.STAFF_PIN;
        Integer otherPin = StaffServiceTestConstants.OTHER_PIN;
        
        when(staffRepository.findByPin(eq(staffPin)))
                .thenReturn(Optional.of(staff));
        when(staffRepository.findByPin(eq(otherPin)))
                .thenReturn(Optional.of(staff));

        ChangePinDTO dto = new ChangePinDTO();
        dto.setCurrentPin(otherPin);
        dto.setNewPin(staffPin);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            staffService.changePin(dto);
        }, "BadRequestException was expected");

        assertEquals("New pin is already in use.", thrown.getMessage());
    }

    @Test
    public void changePin_changeStaffPin_nonexistantPin() {
        ChangePinDTO dto = new ChangePinDTO();
        dto.setCurrentPin(StaffServiceTestConstants.OTHER_PIN);
        dto.setNewPin(StaffServiceTestConstants.STAFF_PIN);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            staffService.changePin(dto);
        }, "NotFoundException was expected");

        assertEquals("Current pin does not exist.", thrown.getMessage());
    }

    @Test
    public void changeSalary_changeStaffSalary_successful() {
        Waiter staff = generateWaiter();
        Long staffId = StaffServiceTestConstants.STAFF_ID;
        when(staffRepository.findById(eq(staffId)))
                .thenReturn(Optional.of(staff));
        
        SetSalaryDTO dto = new SetSalaryDTO();
        dto.setMonthlyWage(BigDecimal.valueOf(1000));
        dto.setStaffId(staffId);

        staffService.changeSalary(dto);
    }

    @Test
    public void changeSalary_changeStaffSalary_nonexistentStaff() {
        SetSalaryDTO dto = new SetSalaryDTO();
        dto.setMonthlyWage(BigDecimal.valueOf(1000));
        Long staffId = StaffServiceTestConstants.STAFF_ID;
        dto.setStaffId(staffId);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            staffService.changeSalary(dto);
        }, "NotFoundException was expected");

        assertEquals(String.format("No staff member with id %d has been found", staffId), thrown.getMessage());
    }

    private Waiter generateWaiter() {
        Waiter waiter = new Waiter();
        waiter.setFirstName(StaffServiceTestConstants.STAFF_FIRST_NAME);
        waiter.setLastName(StaffServiceTestConstants.STAFF_LAST_NAME);
        waiter.setPin(StaffServiceTestConstants.STAFF_PIN);
        waiter.setMonthlyWage(StaffServiceTestConstants.STAFF_WAGE);
        waiter.setPhoneNumber(StaffServiceTestConstants.STAFF_PHONE_NUMBER);
        return waiter;
    }
}
