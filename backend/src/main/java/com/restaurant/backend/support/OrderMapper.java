package com.restaurant.backend.support;

import com.restaurant.backend.domain.Order;
import com.restaurant.backend.dto.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderMapper extends GenericObjectMapper<Order, OrderDTO> {
    private final OrderItemMapper toOrderItemDTO;

    @Override
    public OrderDTO convert(Order source) {
        return new OrderDTO(source.getId(), source.getCreatedAt(),
                source.getNote(), source.getTableId(), toOrderItemDTO.convertAll(source.getOrderItems()),
                source.getWaiter() == null ? null : source.getWaiter().getId());
    }
}
