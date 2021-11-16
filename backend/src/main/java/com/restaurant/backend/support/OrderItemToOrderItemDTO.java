package com.restaurant.backend.support;

import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.dto.OrderItemDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemToOrderItemDTO implements Converter<OrderItem, OrderItemDTO> {
    @Override
    public OrderItemDTO convert(OrderItem source) {
        return new OrderItemDTO(source.getId(), source.getAmount(), source.getOrder().getId(),
                source.getOrderStatus(), source.getItem().getId(),
                source.getCook() == null ? null : source.getCook().getId(),
                source.getBarman() == null ? null : source.getBarman().getId());
    }

    public List<OrderItemDTO> convert(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(this::convert).collect(Collectors.toList());
    }
}
