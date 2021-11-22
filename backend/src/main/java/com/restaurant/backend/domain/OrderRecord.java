package com.restaurant.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at", nullable = false)
    protected LocalDateTime createdAt;

    @Column(name = "amount", nullable = false)
    protected Integer amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_value_id", nullable = false)
    protected ItemValue itemValue;
}
