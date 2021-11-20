package com.restaurant.backend.dto;

import lombok.Getter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ChangePriceDTO {
    @Digits(integer = 11, fraction = 2, message = "Item purchase price can contain a maximum of 11 digits and 2 in fraction")
    @Positive(message = "Item purchase price must be a positive number")
    private BigDecimal purchasePrice;

    @Digits(integer = 11, fraction = 2, message = "Item selling price can contain a maximum of 11 digits and 2 in fraction")
    @Positive(message = "Item selling price must be a positive number")
    private BigDecimal sellingPrice;

    @FutureOrPresent
    private LocalDate fromDate;

    @NotNull(message = "Item id must be provided")
    private Long itemId;
}
