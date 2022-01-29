package com.restaurant.backend.controller;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.StaffDTO;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.dto.requests.ChangePinDTO;
import com.restaurant.backend.dto.requests.SetSalaryDTO;
import com.restaurant.backend.service.StaffService;
import com.restaurant.backend.support.StaffMapper;
import com.restaurant.backend.validation.interfaces.CreateInfo;
import com.restaurant.backend.validation.interfaces.EditInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/staff", produces = MediaType.APPLICATION_JSON_VALUE)
public class StaffController {
    private final StaffService staffService;
    private final StaffMapper staffMapper;

    @GetMapping("/self")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<StaffDTO> getSelf(@AuthenticationPrincipal Staff staff) {
        return new ResponseEntity<>(staffMapper.convert(staff), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<StaffDTO>> getAll() {
        return new ResponseEntity<>(staffMapper.convertAll(staffService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER', 'COOK', 'BARMAN')")
    public ResponseEntity<StaffDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(staffMapper.convert(staffService.findOne(id)), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<StaffDTO> create(@Validated(CreateInfo.class) @RequestBody StaffDTO staffDTO) {
        return new ResponseEntity<>(staffMapper.convert(staffService.create(staffMapper.convertToDomain(staffDTO))), HttpStatus.OK);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<StaffDTO> edit(@Validated(EditInfo.class) @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(staffMapper.convert(staffService.update(userDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        staffService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-salary")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<StaffDTO> setSalary(@Valid @RequestBody SetSalaryDTO dto) {
        return new ResponseEntity<>(staffMapper.convert(staffService.changeSalary(dto)), HttpStatus.OK);
    }

    @PutMapping("/change-pin")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<StaffDTO> changePin(@Valid @RequestBody ChangePinDTO changePinDTO) {
        return new ResponseEntity<>(staffMapper.convert(staffService.changePin(changePinDTO)), HttpStatus.OK);
    }
}
