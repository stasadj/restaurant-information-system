package com.restaurant.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StaffDTO extends UserDTO {
    @Digits(integer = 12, fraction = 2)
    protected BigDecimal monthlyWage;

    @NotNull(message = "Pin is missing.")
    @Min(1000)
    @Max(9999)
    protected Integer pin;
}
