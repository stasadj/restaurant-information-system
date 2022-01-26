package com.restaurant.backend.support;

import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.dto.responses.ItemNameDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemMapper extends GenericObjectMapper<OrderItem, OrderItemDTO> {
    @Override
    public OrderItemDTO convert(OrderItem source) {
        return new OrderItemDTO(source.getId(), source.getAmount(), source.getOrder().getId(),
                source.getOrderStatus(), source.getItem().getId(),
                new ItemNameDTO(source.getItem().getName(), source.getItem().getItemType(), null),
                source.getCook() == null ? null : source.getCook().getId(),
                source.getBarman() == null ? null : source.getBarman().getId());
    }

    public OrderItemDTO convertIncludingPrice(OrderItem source) {
        BigDecimal price = source.getItem().getItemValueAt(source.getOrder().getCreatedAt()).getSellingPrice();
        return new OrderItemDTO(source.getId(), source.getAmount(), source.getOrder().getId(),
                source.getOrderStatus(), source.getItem().getId(),
                new ItemNameDTO(source.getItem().getName(), source.getItem().getItemType(), price),
                source.getCook() == null ? null : source.getCook().getId(),
                source.getBarman() == null ? null : source.getBarman().getId());
    }
}
