package com.restaurant.backend.service;

import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.domain.Staff;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> acceptOrderItems(Staff staff, List<Long> ids);
    List<OrderItem> declineOrderItems(Staff staff, List<Long> ids);
    List<OrderItem> prepareOrderItems(Staff staff, List<Long> ids);
}
