package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
public abstract class PasswordUser extends User {
    @Column(name = "username")
    protected @Getter @Setter String username;

    @Column(name = "password", length = 60)
    protected @Getter @Setter String password;

}