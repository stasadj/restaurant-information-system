package com.restaurant.backend.service;

import com.restaurant.backend.domain.*;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.NotificationRepository;
import com.restaurant.backend.repository.OrderItemRepository;
import com.restaurant.backend.repository.OrderRecordRepository;
import com.restaurant.backend.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderService implements GenericService<Order> {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRecordRepository orderRecordRepository;
    private final NotificationRepository notificationRepository;
    private final StaffService staffService;
    private final ItemService itemService;

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Order findOne(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No order with id %d has been found", id)));
    }

    @Override
    public Order save(Order entity) {
        return orderRepository.save(entity);
    }

    public List<Order> findAllForWaiter(Long id) {
        return orderRepository.findAllByWaiter_Id(id);
    }

    public List<Order> create(OrderDTO order) {
        Staff waiter = staffService.GetById(order.getWaiterId());
        Optional<Order> maybeOrder = orderRepository.findByTableId(order.getTableId());
        if (maybeOrder.isPresent())
            throw new BadRequestException(String.format("Table #%d already has an order.", order.getTableId()));

        Order newOrder = save(new Order(LocalDateTime.now(), order.getNote(), order.getTableId(), (Waiter) waiter));

        for (OrderItemDTO orderItem : order.getOrderItems()) {
            Item item = itemService.getById(orderItem.getItemId());
            newOrder.getOrderItems().add(new OrderItem(orderItem.getAmount(), newOrder, OrderStatus.PENDING, item));
        }
        orderItemRepository.saveAll(newOrder.getOrderItems());

        return findAllForWaiter(order.getWaiterId());
    }

    public List<Order> editOrderItems(OrderDTO order) {
        Order editedOrder = findOne(order.getId());

        for (OrderItemDTO orderItem : order.getOrderItems()) {
            if (orderItem.getId() == null) {
                Item item = itemService.getById(orderItem.getItemId());
                OrderItem newOrderItem = new OrderItem(orderItem.getAmount(), editedOrder, OrderStatus.PENDING, item);
                editedOrder.getOrderItems().add(newOrderItem);
                orderItemRepository.save(newOrderItem);
            } else if (orderItem.getOrderStatus() == OrderStatus.PENDING) {
                orderItemRepository.updateAmount(orderItem.getItemId(), orderItem.getAmount());
            }
        }

        return findAllForWaiter(order.getWaiterId());
    }

    public boolean getHasTablesTaken() {
        return orderRepository.count() > 0;
    }

    public List<OrderRecord> finalizeOrder(Long id) {
        Order order = findOne(id);

        if (order.getOrderItems().stream().anyMatch(orderItem -> orderItem.getOrderStatus() != OrderStatus.READY))
            throw new BadRequestException("Order cannot be finalized, not all order items are ready.");

        List<OrderRecord> records = new ArrayList<>();

        for (OrderItem orderItem : order.getOrderItems()) {
            OrderRecord record = new OrderRecord();
            record.setCreatedAt(order.getCreatedAt());
            record.setAmount(orderItem.getAmount());
            record.setItemValue(orderItem.getItem().getItemValueAt(order.getCreatedAt()));
            records.add(record);
        }
        orderRecordRepository.saveAll(records);

        orderItemRepository.deleteAll(order.getOrderItems());
        notificationRepository.deleteAll(order.getNotifications()); // replace with service method
        orderRepository.delete(order);

        return records;
    }
}
