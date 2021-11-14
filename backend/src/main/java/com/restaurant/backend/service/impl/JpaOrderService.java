package com.restaurant.backend.service.impl;

import com.restaurant.backend.domain.Order;
import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.repository.OrderItemRepository;
import com.restaurant.backend.repository.OrderRepository;
import com.restaurant.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JpaOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public JpaOrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Order findOne(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItems(Long id) {
        return orderItemRepository.findByOrder_IdEquals(id);
    }
}
