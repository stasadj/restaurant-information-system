package com.restaurant.backend.domain;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("manager")
@EqualsAndHashCode(callSuper = true)
@Data
public class Manager extends PasswordUser {

    @Digits(integer = 9, fraction = 2)
    @Column(name = "monthly_wage")
    protected BigDecimal monthlyWage;
}
