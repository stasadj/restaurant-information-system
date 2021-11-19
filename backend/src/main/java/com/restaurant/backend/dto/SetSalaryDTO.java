package com.restaurant.backend.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class SetSalaryDTO {
    @Digits(integer = 11, fraction = 2, message = "Wage can contain a maximum of 11 digits and 2 in fraction")
    @Positive(message = "Wage must be a positive number")
    @NotNull(message = "Wage is missing.")
    private BigDecimal monthlyWage;

    @NotNull(message = "Staff member identifier is missing.")
    private Long staffId;
}
