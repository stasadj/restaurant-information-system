package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("waiter")
public class Waiter extends Staff {

}
