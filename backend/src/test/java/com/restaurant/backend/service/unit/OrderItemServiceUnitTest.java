package com.restaurant.backend.service.unit;

import com.restaurant.backend.domain.*;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.domain.enums.NotificationType;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.responses.DataWithMessage;
import com.restaurant.backend.repository.OrderItemRepository;
import com.restaurant.backend.service.NotificationService;
import com.restaurant.backend.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderItemServiceUnitTest {
    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private OrderItemService orderItemService;

    private List<OrderItem> orderItems;

    @BeforeEach
    public void initOrderItems() {
        Cook cook = new Cook();
        Barman barman = new Barman();
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Item drinkItem = new Item();
        drinkItem.setItemType(ItemType.DRINK);
        Order order = new Order();
        orderItems = Arrays.asList(
            new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null),
            new OrderItem(2L, 1, order, OrderStatus.PENDING, drinkItem, null, null),
            new OrderItem(3L, 1, order, OrderStatus.DECLINED, foodItem, null, null),
            new OrderItem(4L, 1, order, OrderStatus.DECLINED, drinkItem, null, null),
            new OrderItem(5L, 1, order, OrderStatus.IN_PROGRESS, foodItem, cook, null),
            new OrderItem(6L, 1, order, OrderStatus.IN_PROGRESS, drinkItem, null, barman),
            new OrderItem(7L, 1, order, OrderStatus.READY, foodItem, cook, null),
            new OrderItem(8L, 1, order, OrderStatus.READY, drinkItem, null, barman)
        );
    }

    @Test
    public void acceptOrderItems_anOrderItemIsNotFound() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(orderItems.get(0)));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, Arrays.asList(9L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #9 not found.\n", result.getMessage());
        assertSame(cook, orderItems.get(0).getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void acceptOrderItems_anOrderItemIsNotPending() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.of(orderItems.get(4)))
                .thenReturn(Optional.of(orderItems.get(0)));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, Arrays.asList(5L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #5 is not pending.\n", result.getMessage());
        assertSame(cook, orderItems.get(0).getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void acceptOrderItems_anOrderItemCannotBeAccepted() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.of(orderItems.get(1)))
                .thenReturn(Optional.of(orderItems.get(0)));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, Arrays.asList(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("DRINK order item #2 cannot be accepted by cook.\n", result.getMessage());
        assertSame(cook, orderItems.get(0).getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotFound() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(orderItems.get(0)));
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, Arrays.asList(9L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #9 not found.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), anyLong());
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotPending() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.of(orderItems.get(4)))
                .thenReturn(Optional.of(orderItems.get(0)));
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, Arrays.asList(5L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #5 is not pending.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), anyLong());
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void declineOrderItems_anOrderItemCannotBeDeclined() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.of(orderItems.get(1)))
                .thenReturn(Optional.of(orderItems.get(0)));
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, Arrays.asList(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("DRINK order item #2 cannot be declined by cook.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), anyLong());
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void prepareOrderItems_anOrderItemIsNotFound() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(orderItems.get(4)));
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, Arrays.asList(9L, 5L));

        assertEquals(1, result.getData().size());
        assertEquals(5L, result.getData().get(0).getId());
        assertEquals("Order item #9 not found.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), anyLong());
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void prepareOrderItems_anOrderItemIsNotInProgress() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.of(orderItems.get(0)))
                .thenReturn(Optional.of(orderItems.get(4)));
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, Arrays.asList(1L, 5L));

        assertEquals(1, result.getData().size());
        assertEquals(5L, result.getData().get(0).getId());
        assertEquals("Order item #1 is not in progress.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), anyLong());
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void prepareOrderItems_anOrderItemCannotBePrepared() {
        when(orderItemRepository.findById(anyLong()))
                .thenReturn(Optional.of(orderItems.get(5)))
                .thenReturn(Optional.of(orderItems.get(4)));
        Cook cook = new Cook();

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, Arrays.asList(6L, 5L));

        assertEquals(1, result.getData().size());
        assertEquals(5L, result.getData().get(0).getId());
        assertEquals("DRINK order item #6 cannot be prepared by cook.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), anyLong());
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void cancelOrderItems_anOrderItemIsNotFound() {

    }

    @Test
    public void cancelOrderItems_anOrderItemCannotBeAccepted() {

    }
}
