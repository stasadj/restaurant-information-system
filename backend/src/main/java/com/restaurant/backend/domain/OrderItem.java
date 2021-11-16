package com.restaurant.backend.domain;

import javax.persistence.*;

import com.restaurant.backend.domain.enums.OrderStatus;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "amount", nullable = false)
    protected Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    protected Order order;

    @Column(name = "status", nullable = false)
    protected OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    protected Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cook_id")
    protected Cook cook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barman_id")
    protected Barman barman;
}
