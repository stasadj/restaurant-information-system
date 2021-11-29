package com.restaurant.backend.dto.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CredentialsDTO {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
