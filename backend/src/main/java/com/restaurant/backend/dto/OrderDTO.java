package com.restaurant.backend.dto;

import com.restaurant.backend.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String note;
    private Integer tableId;
    private List<OrderItemDTO> orderItems;
    private Long waiterId;
}
