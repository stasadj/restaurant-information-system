package com.restaurant.backend.controller;

import com.restaurant.backend.dto.table.TableOrganizationDTO;
import com.restaurant.backend.service.TableService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/table", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TableController {
    private final TableService tableService;

    @PostMapping("/")
    public ResponseEntity<Boolean> setNewTableOrganization(@RequestBody TableOrganizationDTO dto) {
        boolean success = tableService.setNewTableOrganization(dto);
        return new ResponseEntity<>(success, success ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
