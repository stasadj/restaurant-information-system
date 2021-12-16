package com.restaurant.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PasswordUserDTO extends UserDTO {
    @NotEmpty(message = "Username cannot be empty.")
    protected String username;

    @NotEmpty(message = "Password cannot be empty.")
    protected String password;
}
