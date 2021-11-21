package com.restaurant.backend.controller;

import javax.validation.Valid;

import com.restaurant.backend.dto.table.TableOrganizationDTO;
import com.restaurant.backend.service.TableService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/table", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TableController {
    private TableService tableService;

    @PostMapping("/")
    public Boolean setNewTableOrganization(@Valid @RequestBody TableOrganizationDTO dto) {
        return tableService.setNewTableOrganization(dto);
    }
}
