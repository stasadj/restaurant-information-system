package com.restaurant.backend.service;

import com.restaurant.backend.domain.*;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.repository.OrderItemRepository;
import com.restaurant.backend.repository.OrderRepository;
import com.restaurant.backend.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrderService implements GenericService<Order> {
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

    public List<Order> findAllForWaiter(Long id) { return orderRepository.findAllByWaiter_Id(id); }

    public List<Order> create(OrderDTO order) {
        Staff waiter = staffRepository.findById(order.getWaiterId()).orElse(null);
        Order newOrder = save(new Order(LocalDateTime.now(), order.getNote(), order.getTableId(), (Waiter) waiter));

        for (OrderItemDTO orderItem : order.getOrderItems()){
            Item item = itemRepository.findById(orderItem.getItemId()).orElse(null);
            OrderItem newOrderItem = new OrderItem(orderItem.getAmount(), newOrder, OrderStatus.PENDING, item);
            newOrder.getOrderItems().add(newOrderItem);
            orderItemRepository.save(newOrderItem);
        }

        return findAllForWaiter(order.getWaiterId());
    }

    public List<Order> cancelOrderItem(Long id) {
        OrderItem item = orderItemRepository.findById(id).orElse(null);
        Long waiterId = item.getOrder().getWaiter().getId();

        if (item == null) {
            throw new NotFoundException("Order item does not exist.");
        }

        if (item.getOrderStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("You can't cancel order item that is already in the making.");
        }

        orderItemRepository.deleteById(id);

        return findAllForWaiter(waiterId);
    }

    public List<Order> editOrderItems(OrderDTO order) {
        Order editedOrder = findOne(order.getId());

        for (OrderItemDTO orderItem : order.getOrderItems()) {

            if (orderItem.getId() == null) {
                Item item = itemRepository.findById(orderItem.getItemId()).orElse(null);
                OrderItem newOrderItem = new OrderItem(orderItem.getAmount(), editedOrder, OrderStatus.PENDING, item);
                editedOrder.getOrderItems().add(newOrderItem);
                orderItemRepository.save(newOrderItem);
            }

            else if (orderItem.getOrderStatus() == OrderStatus.PENDING) {
                orderItemRepository.updateAmount(orderItem.getItemId(), orderItem.getAmount());
            }
        }

        return findAllForWaiter(order.getWaiterId());
    }

    public Boolean getHasTablesTaken() {
        return orderRepository.findAllByTableIdIsNotNull().isEmpty() == false;
    }
}
