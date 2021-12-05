package com.restaurant.backend.dto.reports;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.restaurant.backend.dto.reports.enums.ReportGranularity;
import com.restaurant.backend.dto.reports.enums.ReportType;
import com.restaurant.backend.validation.ValueOfEnum;

import lombok.Getter;

public class ReportQueryDTO {
    @Getter
    @NotNull(message = "From date must not be null")
    private LocalDate fromDate;

    @Getter
    @NotNull(message = "To date must not be null")
    private LocalDate toDate;

    @NotNull(message = "Report granularity must not be null")
    @ValueOfEnum(enumClass = ReportGranularity.class, message = "Report granularity is invalid.")
    private String reportGranularity;

    @NotNull(message = "Report type must not be null")
    @ValueOfEnum(enumClass = ReportType.class, message = "Report type is invalid.")
    private String reportType;

    @Getter
    private Long itemId;

    public ReportGranularity getReportGranularity() throws IllegalArgumentException {
        return ReportGranularity.valueOf(reportGranularity);
    }

    public ReportType getReportType() throws IllegalArgumentException {
        return ReportType.valueOf(reportType);
    }
}
