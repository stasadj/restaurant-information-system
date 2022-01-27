package com.restaurant.backend.dto.reports;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class AbstractDateReportResultItemDTO extends AbstractReportResultItemDTO {
    private String type;
    public abstract LocalDate getApproximateDate();
    public abstract String getLabel();
}
