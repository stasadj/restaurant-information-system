package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("barman")
public class Barman extends Staff {

}
