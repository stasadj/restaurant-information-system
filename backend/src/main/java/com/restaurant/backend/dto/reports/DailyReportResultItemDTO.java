package com.restaurant.backend.dto.reports;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DailyReportResultItemDTO extends AbstractDateReportResultItemDTO {
    private LocalDate date;

    @Override
    public LocalDate getApproximateDate() {
        return date;
    }
}
