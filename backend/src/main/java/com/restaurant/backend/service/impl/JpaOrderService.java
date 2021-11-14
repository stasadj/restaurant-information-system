package com.restaurant.backend.service.impl;

import com.restaurant.backend.domain.Order;
import com.restaurant.backend.repository.OrderRepository;
import com.restaurant.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class JpaOrderService implements OrderService {
    private final OrderRepository orderRepository;

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
}
