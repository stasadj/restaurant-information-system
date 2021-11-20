package com.restaurant.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_values")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "purchase_price", nullable = false)
    protected BigDecimal purchasePrice;

    @Column(name = "selling_price", nullable = false)
    protected BigDecimal sellingPrice;

    @Column(name = "from_date")
    protected LocalDate fromDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    protected Item item;
}
