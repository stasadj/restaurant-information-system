package com.restaurant.backend.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangePasswordDTO {
    @NotEmpty(message = "Current password cannot be empty.")
    private String currentPassword;

    @NotEmpty(message = "New password cannot be empty.")
    private String newPassword;
}
