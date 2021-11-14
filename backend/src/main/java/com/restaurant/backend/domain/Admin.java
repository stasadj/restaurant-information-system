package com.restaurant.backend.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("admin")
@NoArgsConstructor
@Getter
@Setter
public class Admin extends PasswordUser  {
}
