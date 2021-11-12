package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("cook")
public class Cook extends Staff {

}
