package com.restaurant.backend.support;

import com.restaurant.backend.domain.Order;
import com.restaurant.backend.dto.OrderDTO;
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
                source.getNote(), source.getTableId(), toOrderItemDTO.convertAll(source.getOrderItems()),
                source.getWaiter() == null ? null : source.getWaiter().getId());
    }

    public List<OrderDTO> convertAll(List<Order> orderList) {
        return orderList.stream().map(this::convert).collect(Collectors.toList());
    }
}
