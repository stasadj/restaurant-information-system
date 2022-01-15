package com.restaurant.backend.support;

import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.dto.responses.ItemNameDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper extends GenericObjectMapper<OrderItem, OrderItemDTO> {
    @Override
    public OrderItemDTO convert(OrderItem source) {
        return new OrderItemDTO(source.getId(), source.getAmount(), source.getOrder().getId(),
                source.getOrderStatus(), source.getItem().getId(),
                new ItemNameDTO(source.getItem().getName(), source.getItem().getItemType()),
                source.getCook() == null ? null : source.getCook().getId(),
                source.getBarman() == null ? null : source.getBarman().getId());
    }
}
