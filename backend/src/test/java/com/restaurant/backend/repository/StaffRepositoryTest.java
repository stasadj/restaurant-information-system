package com.restaurant.backend.repository;

import com.restaurant.backend.domain.Staff;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:staff_service_integration.sql")
public class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    @Test
    public void testSetNewPin() {
        Optional<Staff> staff = staffRepository.findByPin(1000);
        assertTrue(staff.isPresent());
        Long id = staff.get().getId();

        staffRepository.setNewPinForStaff(1234, 1000);

        Optional<Staff> updatedStaff = staffRepository.findByPin(1234);
        assertTrue(updatedStaff.isPresent());
        assertEquals(id, updatedStaff.get().getId());
    }
}
