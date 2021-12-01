package com.restaurant.backend.dto;

import com.restaurant.backend.validation.interfaces.CreateInfo;
import com.restaurant.backend.validation.interfaces.EditInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = "Id cannot be null.", groups = EditInfo.class)
    @Null(message = "Id should be null.", groups = CreateInfo.class)
    protected Long id;

    @NotBlank(message = "First name must not be blank.")
    protected String firstName;

    @NotBlank(message = "Last name must not be blank.")
    protected String lastName;

    @Email(message = "Email should be valid")
    protected String email;

    @NotBlank(message = "Phone number must not be blank.")
    protected String phoneNumber;

    @Pattern(regexp = "waiter|cook|barman|manager|admin", message = "Nonexistent role!")
    protected String role;
}
