package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class PasswordUser extends User {
    @Column(name = "username")
    protected String username;

    @Column(name = "password", length = 60)
    protected String password;
}