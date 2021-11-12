package com.restaurant.backend.domain;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("manager")
public class Manager extends PasswordUser {

    @Digits(integer = 12, fraction = 2)
    @Column(name = "monthly_wage")
    protected BigDecimal monthlyWage;
}
