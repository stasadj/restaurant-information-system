package com.restaurant.backend.controller;

import javax.validation.Valid;

import com.restaurant.backend.dto.reports.ReportQueryDTO;
import com.restaurant.backend.dto.reports.ReportResultsDTO;
import com.restaurant.backend.service.ReportService;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {
    private ReportService reportService;

    @PostMapping("/query")
    @PreAuthorize("hasRole('MANAGER')")
    public ReportResultsDTO queryReport(@Valid @RequestBody ReportQueryDTO query) {
        return reportService.getResults(query);
    }
}
