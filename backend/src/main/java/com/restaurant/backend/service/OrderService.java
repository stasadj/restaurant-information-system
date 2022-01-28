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
    public Optional<Order> findByTableId(Integer tableId) {
        return orderRepository.findByTableId(tableId);
    }

    @Transactional(readOnly = true)
    public List<Order> findAllForWaiter(Long waiterId) {
        return orderRepository.findAllByWaiter_Id(waiterId);
    }

    public Order create(OrderDTO order) throws NotFoundException, BadRequestException {
        boolean isDrink = false, isFood = false;
        Staff waiter = staffService.findOne(order.getWaiterId());
        Optional<Order> maybeOrder = findByTableId(order.getTableId());
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

    public Order createBarOrder(OrderDTO order) throws NotFoundException, BadRequestException {
        Staff barman = staffService.findOne(order.getWaiterId());
        Optional<Order> maybeOrder = findByTableId(order.getTableId());
        if (maybeOrder.isPresent())
            throw new BadRequestException(String.format("Table #%d already has an order.", order.getTableId()));

        Order newOrder = orderRepository.save(new Order(LocalDateTime.now(), order.getNote(), order.getTableId(), null));

        for (OrderItemDTO orderItem : order.getOrderItems()) {
            Item item = itemService.findOne(orderItem.getItemId());
            if (item.getItemType() == ItemType.FOOD) continue;
            OrderItem newOrderItem = new OrderItem(orderItem.getAmount(), newOrder, OrderStatus.IN_PROGRESS, item);
            newOrderItem.setBarman((Barman) barman);
            orderItemService.save(newOrderItem);
            newOrder.getOrderItems().add(newOrderItem);
        }
        return newOrder;
    }

    public Order editOrder(Staff staff, OrderDTO orderDTO) throws NotFoundException {
        Order editedOrder = findOne(orderDTO.getId());
        editedOrder.setNote(orderDTO.getNote());
        boolean isBarOrder = editedOrder.getWaiter() == null;
        boolean isBarman = staff instanceof Barman;
        if (!isBarOrder && isBarman)
            return editedOrder;  // barman can edit only bar orders

        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            if (orderItemDTO.getId() == null) {
                Item item = itemService.findOne(orderItemDTO.getItemId());
                if (item.getItemType() == ItemType.FOOD && isBarOrder) continue;
                OrderItem newOrderItem = new OrderItem(orderItemDTO.getAmount(), editedOrder, OrderStatus.PENDING, item);
                if (isBarman) {
                    newOrderItem.setBarman((Barman) staff);
                    newOrderItem.setOrderStatus(OrderStatus.IN_PROGRESS);
                }
                orderItemService.save(newOrderItem);
            } else {
                OrderItem editedOrderItem = orderItemService.findOne(orderItemDTO.getId());
                if (editedOrderItem.getAmount().intValue() == orderItemDTO.getAmount().intValue()) continue;
                if (editedOrderItem.getOrderStatus() == OrderStatus.PENDING) {
                    editedOrderItem.setAmount(orderItemDTO.getAmount());
                    orderItemService.save(editedOrderItem);
                } else if (editedOrderItem.getAmount() < orderItemDTO.getAmount()) {
                    OrderItem newOrderItem = new OrderItem(orderItemDTO.getAmount() - editedOrderItem.getAmount(),
                            editedOrder, OrderStatus.PENDING, editedOrderItem.getItem());
                    if (isBarman) {
                        newOrderItem.setBarman((Barman) staff);
                        newOrderItem.setOrderStatus(OrderStatus.IN_PROGRESS);
                    }
                    orderItemService.save(newOrderItem);
                } else {
                    editedOrder.setNote(editedOrder.getNote() +
                            String.format("\nTried to decrease amount of order item #%d ('%s') to %d.",
                                    editedOrderItem.getId(), editedOrderItem.getItem().getName(), orderItemDTO.getAmount()));
                }

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
