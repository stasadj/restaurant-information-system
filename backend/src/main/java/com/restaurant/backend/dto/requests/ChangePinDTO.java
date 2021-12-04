package com.restaurant.backend.dto.requests;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
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
