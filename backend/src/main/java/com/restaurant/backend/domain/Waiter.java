package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@DiscriminatorValue("waiter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Waiter extends Staff {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "waiter")
    protected Set<Order> currentOrders;

    public String getRole() {
        return "waiter";
    }
}
