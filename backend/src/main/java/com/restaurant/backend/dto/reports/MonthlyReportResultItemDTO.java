package com.restaurant.backend.dto.reports;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MonthlyReportResultItemDTO extends AbstractDateReportResultItemDTO {
    private Month month;
    private Year year;

    @Override
    public LocalDate getApproximateDate() {
        return LocalDate.of(year.getValue(), month, 1);
    }
}
