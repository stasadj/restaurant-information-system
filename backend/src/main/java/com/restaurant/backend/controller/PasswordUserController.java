package com.restaurant.backend.controller;

import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.dto.PasswordUserDTO;
import com.restaurant.backend.dto.UserDTO;
import com.restaurant.backend.dto.requests.ChangePasswordDTO;
import com.restaurant.backend.dto.requests.ChangeUsernameDTO;
import com.restaurant.backend.service.JWTUserDetailsService;
import com.restaurant.backend.service.PasswordUserService;
import com.restaurant.backend.support.PasswordUserMapper;
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
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class PasswordUserController {
    private final PasswordUserService passwordUserService;
    private final JWTUserDetailsService userDetailsService;
    private final PasswordUserMapper passwordUserMapper;

    @GetMapping("/self")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<PasswordUserDTO> getSelf(@AuthenticationPrincipal PasswordUser user) {
        return new ResponseEntity<>(passwordUserMapper.convert(user), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PasswordUserDTO>> getAll() {
        return new ResponseEntity<>(passwordUserMapper.convertAll(passwordUserService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PasswordUserDTO getById(@PathVariable Long id) {
        return passwordUserMapper.convert(passwordUserService.findOne(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PasswordUserDTO> create(@Validated(CreateInfo.class) @RequestBody PasswordUserDTO passwordUserDTO) {
        return new ResponseEntity<>(passwordUserMapper.convert(passwordUserService.create(passwordUserMapper.convertToDomain(passwordUserDTO))), HttpStatus.OK);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PasswordUserDTO> edit(@Validated(EditInfo.class) @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(passwordUserMapper.convert(passwordUserService.update(userDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        passwordUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-username")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PasswordUserDTO> changeUsername(@Valid @RequestBody ChangeUsernameDTO dto) {
        return new ResponseEntity<>(passwordUserMapper.convert(passwordUserService.changeUsername(dto)), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        boolean success = userDetailsService.changePassword(dto);
        return new ResponseEntity<>(success? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
