package com.restaurant.backend.service;

import com.restaurant.backend.domain.Order;
import com.restaurant.backend.dto.OrderDTO;

public interface OrderService extends GenericService<Order> {
    Order create(OrderDTO order);
}
