package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("barman")
@EqualsAndHashCode(callSuper = true)
@Data
public class Barman extends Staff {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "barman")
    protected Set<Order> currentOrders;
}
