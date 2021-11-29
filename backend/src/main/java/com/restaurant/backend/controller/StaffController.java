package com.restaurant.backend.controller;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.StaffDTO;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.dto.requests.SetSalaryDTO;
import com.restaurant.backend.service.StaffService;
import com.restaurant.backend.support.StaffMapper;
import com.restaurant.backend.support.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/staff", produces = MediaType.APPLICATION_JSON_VALUE)
public class StaffController {
    private final StaffService staffService;
    private final StaffMapper staffMapper;
    private final UserMapper userMapper;

    @GetMapping("/self")
    @PreAuthorize("hasRole('STAFF')")
    public StaffDTO getSelf(@AuthenticationPrincipal Staff staff) {
        return staffMapper.convertToDto(staff);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER')")
    public List<StaffDTO> getAll() {
        return staffMapper.convert(staffService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public StaffDTO getById(@PathVariable Long id) {
        return staffMapper.convertToDto(staffService.findOne(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<StaffDTO> create(@Valid @RequestBody StaffDTO staffDTO) {
        return new ResponseEntity<>(staffMapper.convertToDto(staffService.create(staffMapper.convertToDomain(staffDTO))), HttpStatus.OK);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<StaffDTO> edit(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(staffMapper.convertToDto(staffService.update(userMapper.convertToDomain(userDTO))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        staffService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-salary")
    @PreAuthorize("hasRole('MANAGER')")
    public StaffDTO setSalary(@Valid @RequestBody SetSalaryDTO dto) {
        return staffMapper.convertToDto(staffService.changeSalary(dto));
    }

    @PutMapping("/change-pin")
    @PreAuthorize("hasRole('MANAGER')")
    public StaffDTO changePin() {
        //TODO
        return staffMapper.convertToDto(staffService.changePin());
    }
}
