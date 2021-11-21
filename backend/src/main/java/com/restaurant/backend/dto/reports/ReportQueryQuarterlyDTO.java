package com.restaurant.backend.dto.reports;

import java.time.Year;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class ReportQueryQuarterlyDTO {
    @NotNull
    private Year year;

    private Integer itemId;
}
