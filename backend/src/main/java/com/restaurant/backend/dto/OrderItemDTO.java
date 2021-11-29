package com.restaurant.backend.dto;

import com.restaurant.backend.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemDTO {
    private Long id;

    @Positive(message = "Amount must be a positive number.")
    @NotNull(message = "Amount is missing.")
    private Integer amount;

    @NotNull(message = "Order id is missing.")
    private Long orderId;

    private OrderStatus orderStatus;

    @NotNull(message = "Item id is missing.")
    private Long itemId;

    private Long cookId;

    private Long barmanId;
}
