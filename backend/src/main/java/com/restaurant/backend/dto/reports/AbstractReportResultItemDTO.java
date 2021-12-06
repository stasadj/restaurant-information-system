package com.restaurant.backend.dto.reports;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DailyReportResultItemDTO.class, name = "Daily"),
    @JsonSubTypes.Type(value = WeeklyReportResultItemDTO.class, name = "Weekly"),
    @JsonSubTypes.Type(value = MonthlyReportResultItemDTO.class, name = "Monthly"),
    @JsonSubTypes.Type(value = QuarterlyReportResultItemDTO.class, name = "Quarterly"),
    @JsonSubTypes.Type(value = YearlyReportResultItemDTO.class, name = "Yearly"),
    @JsonSubTypes.Type(value = ItemReportResultItemDTO.class, name = "Item")
})
public abstract class AbstractReportResultItemDTO {
    protected BigDecimal grossIncome = BigDecimal.ZERO;
    protected BigDecimal expenses = BigDecimal.ZERO;

    public void addIncome(BigDecimal income) {
        grossIncome = grossIncome.add(income);
    }

    public void addExpense(BigDecimal expense) {
        expenses = expenses.add(expense);
    }
}
