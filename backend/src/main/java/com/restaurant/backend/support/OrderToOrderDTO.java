package com.restaurant.backend.support;

import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.domain.Order;
import com.restaurant.backend.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderToOrderDTO implements Converter<Order, OrderDTO> {
    private final OrderItemToOrderItemDTO toOrderItemDTO;

    @Override
    public OrderDTO convert(Order source) {
        return new OrderDTO(source.getId(), source.getCreatedAt(),
                source.getNote(), source.getTableId(), convertItems(source.getOrderItems()), source.getWaiter().getId());
    }

    public List<OrderDTO> convert(List<Order> orderList) {
        return orderList.stream().map(this::convert).collect(Collectors.toList());
    }

    private OrderItemDTO convert(OrderItem item) {
        return toOrderItemDTO.convert(item);
    }

    private List<OrderItemDTO> convertItems(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::convert).collect(Collectors.toList());
    }
}
