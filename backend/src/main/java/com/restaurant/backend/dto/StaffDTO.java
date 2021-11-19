package com.restaurant.backend.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StaffDTO extends UserDTO {
    @Digits(integer = 12, fraction = 2)
    protected BigDecimal monthlyWage;

    @Min(1000)
    @Max(9999)
    protected Integer pin;
}
