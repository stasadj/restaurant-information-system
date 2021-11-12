package com.restaurant.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_values")
@Data
public class ItemValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(name = "purchase_price", nullable = false)
    protected BigDecimal purchasePrice;

    @Column(name = "selling_price", nullable = false)
    protected BigDecimal sellingPrice;

    @Column(name = "from_date")
    protected LocalDateTime fromDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    protected Item item;
}
