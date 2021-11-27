package com.restaurant.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "waiter_id")
    protected Waiter waiter;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    protected List<Notification> notifications;

    protected BigDecimal getTotal() {
        return orderItems.stream()
                .map(orderItem -> orderItem.getItem().getItemValueAt(LocalDateTime.now()).getSellingPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order(LocalDateTime createdAt, String note, Integer tableId, Waiter waiter) {
        this.createdAt = createdAt;
        this.note = note;
        this.tableId = tableId;
        this.waiter = waiter;
        this.orderItems = new ArrayList<>();
    }
}
