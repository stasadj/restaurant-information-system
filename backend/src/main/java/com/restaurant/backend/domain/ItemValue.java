package com.restaurant.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
	protected LocalDateTime fromDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	protected Item item;
}
