package com.restaurant.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurant_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;

    @Column(name = "note")
    protected String note;

    @Column(name = "table_id", nullable = false)
    protected Integer tableId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    protected List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barman_id")
    protected Barman barman;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id")
    protected Waiter waiter;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    protected List<Notification> notifications;

    protected BigDecimal getTotal() {
        return orderItems.stream()
                .map(orderItem -> orderItem.getItem().getItemValueAt(LocalDateTime.now()).getSellingPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
