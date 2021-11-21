package com.restaurant.backend.dto.reports;

import java.time.LocalDate;
import java.time.Year;

import com.restaurant.backend.dto.enums.QuarterOfYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QuarterlyReportResultItemDTO extends AbstractDateReportResultItemDTO {
    private Year year;
    private QuarterOfYear quarter;

    @Override
    public LocalDate getApproximateDate() {
        return LocalDate.ofYearDay(year.getValue(), (quarter.getValue() - 1) * 13 + 1);
    }
}
