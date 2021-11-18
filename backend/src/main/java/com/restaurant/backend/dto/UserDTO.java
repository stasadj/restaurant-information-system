package com.restaurant.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String role;
}
