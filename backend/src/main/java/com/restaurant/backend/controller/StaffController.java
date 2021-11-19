package com.restaurant.backend.controller;

import java.util.List;

import javax.validation.Valid;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.SetSalaryDTO;
import com.restaurant.backend.dto.StaffDTO;
import com.restaurant.backend.service.StaffService;
import com.restaurant.backend.support.StaffMapper;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/staff", produces = MediaType.APPLICATION_JSON_VALUE)
public class StaffController {
    private final StaffService staffService;
    private final StaffMapper staffMapper;

    @GetMapping("/self")
    @PreAuthorize("hasRole('STAFF')")
    public StaffDTO getSelf(@AuthenticationPrincipal Staff staff) {
        return staffMapper.convertToDto(staff);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER')")
    public List<StaffDTO> getAll() {
        return staffMapper.convert(staffService.GetAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public StaffDTO getById(@PathVariable Long id) {
        return staffMapper.convertToDto(staffService.GetById(id));
    }

    @PutMapping("/change-salary")
    @PreAuthorize("hasRole('MANAGER')")
    public StaffDTO setSalary(@Valid @RequestBody SetSalaryDTO dto) {
        return staffMapper.convertToDto(staffService.SetSalary(dto));
    }
}
