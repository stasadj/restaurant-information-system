package com.restaurant.backend.service;

import com.restaurant.backend.domain.*;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.domain.enums.NotificationType;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
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
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final OrderRecordService orderRecordService;
    private final NotificationService notificationService;
    private final StaffService staffService;
    private final ItemService itemService;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findOne(Long id) throws NotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No order with id %d has been found", id)));
    }

    @Transactional(readOnly = true)
    public List<Order> findAllForWaiter(Long id) {
        return orderRepository.findAllByWaiter_Id(id);
    }

    public Order create(OrderDTO order) throws NotFoundException, BadRequestException {
        boolean isDrink = false, isFood = false;
        Staff waiter = staffService.findOne(order.getWaiterId());
        Optional<Order> maybeOrder = orderRepository.findByTableId(order.getTableId());
        if (maybeOrder.isPresent())
            throw new BadRequestException(String.format("Table #%d already has an order.", order.getTableId()));

        Order newOrder = orderRepository.save(new Order(LocalDateTime.now(), order.getNote(), order.getTableId(), (Waiter) waiter));

        for (OrderItemDTO orderItem : order.getOrderItems()) {
            Item item = itemService.findOne(orderItem.getItemId());
            if (item.getItemType() == ItemType.DRINK) isDrink = true;
            else isFood = true;
            OrderItem newOrderItem = new OrderItem(orderItem.getAmount(), newOrder, OrderStatus.PENDING, item);
            orderItemService.save(newOrderItem);
            newOrder.getOrderItems().add(newOrderItem);
        }

        if (isDrink)
            notificationService.createNotification("NEW ORDER", NotificationType.WAITER_BARMAN, newOrder);
        if (isFood)
            notificationService.createNotification("NEW ORDER", NotificationType.WAITER_COOK, newOrder);

        return newOrder;
    }

    public Order editOrder(OrderDTO order) throws NotFoundException {
        Order editedOrder = findOne(order.getId());
        editedOrder.setNote(order.getNote());

        for (OrderItemDTO orderItem : order.getOrderItems()) {
            if (orderItem.getId() == null) {
                Item item = itemService.findOne(orderItem.getItemId());
                OrderItem newOrderItem = new OrderItem(orderItem.getAmount(), editedOrder, OrderStatus.PENDING, item);
                orderItemService.save(newOrderItem);
            } else if (orderItem.getOrderStatus() == OrderStatus.PENDING) {
                OrderItem editedOrderItem = orderItemService.findOne(orderItem.getId());
                editedOrderItem.setAmount(orderItem.getAmount());
                orderItemService.save(editedOrderItem);
            }
        }
        return orderRepository.save(editedOrder);
    }

    public boolean getHasTablesTaken() {
        return orderRepository.count() > 0;
    }

    public List<OrderRecord> finalizeOrder(Long id) throws NotFoundException, BadRequestException {
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
        records = orderRecordService.saveAll(records);

        orderItemService.deleteAll(order.getOrderItems());
        notificationService.deleteAll(order.getNotifications());
        orderRepository.delete(order);

        return records;
    }
}
