package com.restaurant.backend.service.impl;

import com.restaurant.backend.domain.*;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.repository.OrderItemRepository;
import com.restaurant.backend.repository.OrderRepository;
import com.restaurant.backend.repository.StaffRepository;
import com.restaurant.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class JpaOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StaffRepository staffRepository;
    private final ItemRepository itemRepository;

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
    public Order create(OrderDTO order) {
        Staff waiter = staffRepository.findById(order.getWaiterId()).orElse(null);
        Order newOrder = save(new Order(LocalDateTime.now(), order.getNote(), order.getTableId(), (Waiter) waiter));

        for (OrderItemDTO orderItem : order.getOrderItems()){
            Item item = itemRepository.findById(orderItem.getItemId()).orElse(null);
            OrderItem newOrderItem = new OrderItem(orderItem.getAmount(), newOrder, OrderStatus.PENDING, item);
            newOrder.getOrderItems().add(newOrderItem);
            orderItemRepository.save(newOrderItem);
        }

        return newOrder;
    }
}
