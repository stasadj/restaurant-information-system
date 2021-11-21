package com.restaurant.backend.dto.reports;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemReportResultItemDTO extends AbstractReportResultItemDTO {
    public ItemReportResultItemDTO(String name) {
        this.name = name;
        quantity = 0;
        grossIncome = BigDecimal.ZERO;
        expenses = BigDecimal.ZERO;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    private String name;
    private Integer quantity;
}
