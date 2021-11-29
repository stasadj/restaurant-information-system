package com.restaurant.backend.dto.requests;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class ChangePinDTO {

    @NotNull(message = "Current pin is missing.")
    @Min(1000)
    @Max(9999)
    private Integer currentPin;

    @NotNull(message = "New pin is missing.")
    @Min(1000)
    @Max(9999)
    private Integer newPin;
}
