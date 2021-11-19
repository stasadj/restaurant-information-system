package com.restaurant.backend.service;

import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.SetSalaryDTO;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.StaffRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class StaffService {
    private StaffRepository staffRepository;

    public List<Staff> GetAll() {
        return staffRepository.findAll();
    }

    public Staff GetById(Long id) throws NotFoundException {
        return staffRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("No staff member with id %d has been found", id)));
    }

    public Staff SetSalary(SetSalaryDTO dto) throws NotFoundException {
        Staff staff = GetById(dto.getStaffId());
        staff.setMonthlyWage(dto.getMonthlyWage());
        return staffRepository.save(staff);
    }
}
