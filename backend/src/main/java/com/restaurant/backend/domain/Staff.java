package com.restaurant.backend.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
public abstract class Staff extends User {
    @Digits(integer = 12, fraction = 2)
    @Column(name = "monthly_wage")
    protected @Getter @Setter BigDecimal monthlyWage;

    @Column(name = "pin")
    protected @Getter @Setter int pin;

    @Override
    public String getUsername() {
        return String.valueOf(getPin());
    }

    @Override
    public String getPassword() {
        return getUsername();
    }
}
