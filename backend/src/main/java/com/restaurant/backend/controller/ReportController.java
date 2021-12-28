package com.restaurant.backend.controller;

import com.restaurant.backend.dto.reports.ReportQueryDTO;
import com.restaurant.backend.dto.reports.ReportResultsDTO;
import com.restaurant.backend.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/query")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ReportResultsDTO> queryReport(@Valid ReportQueryDTO query) {
        return new ResponseEntity<>(reportService.getResults(query), HttpStatus.OK);
    }
}
