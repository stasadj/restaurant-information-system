package com.restaurant.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public abstract class PasswordUser extends User {
    @Column(name = "username")
    protected String username;

    @Column(name = "password", length = 60)
    protected String password;
}