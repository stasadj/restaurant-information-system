package com.restaurant.backend.service;

import com.restaurant.backend.domain.Order;
import com.restaurant.backend.domain.OrderItem;

import java.util.List;

public interface OrderService extends GenericService<Order> {
    List<OrderItem> getOrderItems(Long id);
}
