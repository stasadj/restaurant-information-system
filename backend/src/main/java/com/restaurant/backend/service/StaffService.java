package com.restaurant.backend.service;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.domain.User;
import com.restaurant.backend.dto.requests.SetSalaryDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;

    public List<Staff> getAll() {
        return staffRepository.findAll();
    }

    public Staff findOne(Long id) throws NotFoundException {
        return staffRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("No staff member with id %d has been found", id)));
    }

    public Staff create(Staff staff) {
        staff.setId(null);
        staff.setDeleted(false);
        Optional<Staff> maybeStaff = staffRepository.findByPin(staff.getPin());
        if (maybeStaff.isPresent())
            throw new BadRequestException(String.format("Staff member with pin %d already exists.", staff.getPin()));
        return staffRepository.save(staff);
    }

    public Staff update(User user) throws NotFoundException {
        Staff staff = findOne(user.getId());
        staff.setFirstName(user.getFirstName());
        staff.setLastName(user.getLastName());
        staff.setEmail(user.getEmail());
        staff.setPhoneNumber(user.getPhoneNumber());
        return staffRepository.save(staff);
    }

    public void delete(Long id) throws NotFoundException {
        Staff staff = findOne(id);
        staffRepository.delete(staff);
    }

    public Staff changePin() throws NotFoundException {
        // TODO
        return null;
    }

    public Staff changeSalary(SetSalaryDTO dto) throws NotFoundException {
        Staff staff = findOne(dto.getStaffId());
        staff.setMonthlyWage(dto.getMonthlyWage());
        return staffRepository.save(staff);
    }
}
