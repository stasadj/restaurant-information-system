package com.restaurant.backend.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Staff extends User {

    @Digits(integer = 12, fraction = 2)
    @Column(name = "monthly_wage")
    protected BigDecimal monthlyWage;

    @Column(name = "pin")
    protected Integer pin;

    @Override
    public String getUsername() {
        return pin.toString();
    }

    @Override
    public String getPassword() {
        return getUsername();
    }
}
