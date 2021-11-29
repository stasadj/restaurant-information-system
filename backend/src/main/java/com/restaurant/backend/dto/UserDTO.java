package com.restaurant.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    protected Long id;

    @NotBlank(message = "First name must not be blank.")
    protected String firstName;

    @NotBlank(message = "Last name must not be blank.")
    protected String lastName;

    @Email(message = "Email should be valid")
    protected String email;

    @NotBlank(message = "Phone number must not be blank.")
    protected String phoneNumber;

    protected String role;
}
