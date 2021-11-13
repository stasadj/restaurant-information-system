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
@DiscriminatorValue("cook")
@EqualsAndHashCode(callSuper = true)
@Data
public class Cook extends Staff {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cook")
    protected Set<OrderItem> currentOrderItems;
}
