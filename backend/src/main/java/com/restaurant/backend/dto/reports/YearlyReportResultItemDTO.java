package com.restaurant.backend.dto.reports;

import java.time.LocalDate;
import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YearlyReportResultItemDTO extends AbstractDateReportResultItemDTO {
    private Year year;

    @Override
    public LocalDate getApproximateDate() {
        return LocalDate.ofYearDay(year.getValue(), 1);
    }
}
