package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@DiscriminatorValue("barman")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Barman extends Staff {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "barman")
    protected Set<Order> currentOrders;
}
