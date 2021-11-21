package com.restaurant.backend.dto.reports;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
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
