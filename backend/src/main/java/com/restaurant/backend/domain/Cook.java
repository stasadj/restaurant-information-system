package com.restaurant.backend.domain;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@DiscriminatorValue("cook")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cook extends Staff {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cook")
    protected Set<OrderItem> currentOrderItems;

    public String getRole() {
        return "cook";
    }
}
