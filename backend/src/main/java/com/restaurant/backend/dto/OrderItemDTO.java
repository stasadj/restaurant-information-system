package com.restaurant.backend.dto;

import com.restaurant.backend.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemDTO {
    private Long id;
    private Integer amount;
    private Long orderId;
    private OrderStatus orderStatus;
    private Long itemId;
    private Long cookId;
    private Long barmanId;
}
