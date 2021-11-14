package com.restaurant.backend.domain;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("manager")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Manager extends PasswordUser {

    @Digits(integer = 9, fraction = 2)
    @Column(name = "monthly_wage")
    protected BigDecimal monthlyWage;
}
