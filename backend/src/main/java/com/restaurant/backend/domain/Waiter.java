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
@DiscriminatorValue("waiter")
@EqualsAndHashCode(callSuper = true)
@Data
public class Waiter extends Staff {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "waiter")
    protected Set<Order> currentOrders;
}