package com.restaurant.backend.dto.reports;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DailyReportResultItemDTO extends AbstractDateReportResultItemDTO {
    private LocalDate date;

    @Override
    public LocalDate getApproximateDate() {
        return date;
    }
}
