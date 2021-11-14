package com.restaurant.backend.support;

import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.domain.Order;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderToOrderDTO implements Converter<Order, OrderDTO> {
    private final OrderService orderService;
    private final OrderItemToOrderItemDTO toOrderItemDTO;

    @Autowired
    public OrderToOrderDTO(OrderService orderService, OrderItemToOrderItemDTO toOrderItemDTO) {
        this.orderService = orderService;
        this.toOrderItemDTO = toOrderItemDTO;
    }

    @Override
    public OrderDTO convert(Order source) {
        return new OrderDTO(source.getId(), source.getCreatedAt(),
                source.getNote(), source.getTableId(), convertItems(orderService.getOrderItems(source.getId())),
                source.getBarman() == null ? null : source.getBarman().getId(), source.getWaiter().getId());
    }

    public List<OrderDTO> convert(List<Order> orderList) {
        return orderList.stream().map(this::convert).collect(Collectors.toList());
    }

    private List<OrderItemDTO> convertItems(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::convert).collect(Collectors.toList());
    }

    private OrderItemDTO convert(OrderItem item) {
        return toOrderItemDTO.convert(item);
    }
}
