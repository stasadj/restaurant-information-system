package com.restaurant.backend.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ChangeUsernameDTO {
    @NotEmpty(message = "Current username cannot be empty.")
    private String currentUsername;

    @NotEmpty(message = "New username cannot be empty.")
    private String newUsername;
}
