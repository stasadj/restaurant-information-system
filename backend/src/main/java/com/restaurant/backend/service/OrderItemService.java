package com.restaurant.backend.service;

import com.restaurant.backend.domain.*;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.responses.DataWithMessage;
import com.restaurant.backend.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void saveAll(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }

    public void deleteAll(List<OrderItem> orderItems) {
        orderItemRepository.deleteAll(orderItems);
    }

    public void updateAmount(Long id, Integer amount) {
        orderItemRepository.updateAmount(id, amount);
    }

    public void setStatusForOrderItem(Integer status, Long id) {
        orderItemRepository.setStatusForOrderItem(status, id);
    }

    public DataWithMessage<List<OrderItem>> acceptOrderItems(Staff staff, List<Long> ids) {
        boolean isCook = staff instanceof Cook;
        List<OrderItem> acceptedItems = new ArrayList<>();
        StringBuilder messageBuilder = new StringBuilder();
        for (Long id : ids) {
            Optional<OrderItem> maybeOrderItem = orderItemRepository.findById(id);
            if (maybeOrderItem.isEmpty()) {
                messageBuilder.append("Order item #").append(id).append(" not found.\n");
                continue;
            }
            OrderItem orderItem = maybeOrderItem.get();
            if (orderItem.getOrderStatus() != OrderStatus.PENDING) {
                messageBuilder.append("Order item #").append(id).append(" is not pending.\n");
                continue;
            }
            ItemType type = orderItem.getItem().getItemType();
            if (type == ItemType.DRINK && isCook || type == ItemType.FOOD && !isCook) {
                messageBuilder.append(type).append(" order item #").append(id).append(" cannot be accepted by ")
                        .append(isCook ? "cook" : "barman").append(".\n");
                continue;
            }
            orderItem.setOrderStatus(OrderStatus.IN_PROGRESS);
            if (isCook)
                orderItem.setCook((Cook) staff);
            else
                orderItem.setBarman((Barman) staff);
            orderItemRepository.save(orderItem);
            acceptedItems.add(orderItem);
        }
        String message = messageBuilder.toString();
        return new DataWithMessage<>(acceptedItems, message);
    }

    public DataWithMessage<List<OrderItem>> declineOrderItems(Staff staff, List<Long> ids) {
        boolean isCook = staff instanceof Cook;
        List<OrderItem> declinedItems = new ArrayList<>();
        StringBuilder messageBuilder = new StringBuilder();
        for (Long id : ids) {
            Optional<OrderItem> maybeOrderItem = orderItemRepository.findById(id);
            if (maybeOrderItem.isEmpty()) {
                messageBuilder.append("Order item #").append(id).append(" not found.\n");
                continue;
            }
            OrderItem orderItem = maybeOrderItem.get();
            if (orderItem.getOrderStatus() != OrderStatus.PENDING) {
                messageBuilder.append("Order item #").append(id).append(" is not pending.\n");
                continue;
            }
            ItemType type = orderItem.getItem().getItemType();
            if (type == ItemType.DRINK && isCook || type == ItemType.FOOD && !isCook) {
                messageBuilder.append(type).append(" order item #").append(id).append(" cannot be declined by ")
                        .append(isCook ? "cook" : "barman").append(".\n");
                continue;
            }
            orderItem.setOrderStatus(OrderStatus.DECLINED);
            orderItemRepository.setStatusForOrderItem(OrderStatus.DECLINED.ordinal(), id);
            declinedItems.add(orderItem);

            orderItem.getOrder().getNotifications().add(new Notification(/* TODO */));
        }
        String message = messageBuilder.toString();
        return new DataWithMessage<>(declinedItems, message);
    }

    public DataWithMessage<List<OrderItem>> prepareOrderItems(Staff staff, List<Long> ids) {
        boolean isCook = staff instanceof Cook;
        List<OrderItem> preparedItems = new ArrayList<>();
        StringBuilder messageBuilder = new StringBuilder();
        for (Long id : ids) {
            Optional<OrderItem> maybeOrderItem = orderItemRepository.findById(id);
            if (maybeOrderItem.isEmpty()) {
                messageBuilder.append("Order item #").append(id).append(" not found.\n");
                continue;
            }
            OrderItem orderItem = maybeOrderItem.get();
            if (orderItem.getOrderStatus() != OrderStatus.IN_PROGRESS) {
                messageBuilder.append("Order item #").append(id).append(" is not in progress.\n");
                continue;
            }
            ItemType type = orderItem.getItem().getItemType();
            if (type == ItemType.DRINK && isCook || type == ItemType.FOOD && !isCook) {
                messageBuilder.append(type).append(" order item #").append(id).append(" cannot be prepared by ")
                        .append(isCook ? "cook" : "barman").append(".\n");
                continue;
            }
            orderItem.setOrderStatus(OrderStatus.READY);
            orderItemRepository.setStatusForOrderItem(OrderStatus.READY.ordinal(), id);
            preparedItems.add(orderItem);

            orderItem.getOrder().getNotifications().add(new Notification(/* TODO */));
        }
        String message = messageBuilder.toString();
        return new DataWithMessage<>(preparedItems, message);
    }

    public DataWithMessage<List<Long>> cancelOrderItems(List<Long> ids) {
        List<Long> deletedItemIds = new ArrayList<>();
        StringBuilder messageBuilder = new StringBuilder();
        for (Long id : ids) {
            Optional<OrderItem> maybeOrderItem = orderItemRepository.findById(id);
            if (maybeOrderItem.isEmpty()) {
                messageBuilder.append("Order item #").append(id).append(" not found.\n");
                continue;
            }
            OrderItem orderItem = maybeOrderItem.get();
            if (orderItem.getOrderStatus() != OrderStatus.PENDING && orderItem.getOrderStatus() != OrderStatus.DECLINED) {
                messageBuilder.append("Order item #").append(id).append(" cannot be cancelled.\n");
                continue;
            }
            orderItemRepository.deleteById(id);
            deletedItemIds.add(id);
        }
        return new DataWithMessage<>(deletedItemIds, messageBuilder.toString());
    }
}
