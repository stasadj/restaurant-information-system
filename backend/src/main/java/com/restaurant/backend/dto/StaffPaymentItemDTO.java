package com.restaurant.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffPaymentItemDTO {
    protected Long id;

    @NotNull(message = "Amount cannot be null.")
    @Digits(integer = 12, fraction = 2)
    protected BigDecimal amount;

    @NotNull(message = "Date and time cannot be null.")
    protected LocalDateTime dateTime;

    @NotNull(message = "User id cannot be null.")
    protected Long userId;

}
