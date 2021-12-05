package com.restaurant.backend.service.unit;

import com.restaurant.backend.domain.*;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.domain.enums.NotificationType;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.responses.DataWithMessage;
import com.restaurant.backend.repository.OrderItemRepository;
import com.restaurant.backend.service.NotificationService;
import com.restaurant.backend.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @Test
    public void acceptOrderItems_anOrderItemIsNotFound() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.empty());
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #2 not found.\n", result.getMessage());
        assertSame(cook, orderItem1.getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void acceptOrderItems_anOrderItemIsNotPending() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, order, OrderStatus.IN_PROGRESS, foodItem, cook, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #2 is not pending.\n", result.getMessage());
        assertSame(cook, orderItem1.getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(eq(orderItem1));
        verify(orderItemRepository, never()).save(eq(orderItem2));
    }

    @Test
    public void acceptOrderItems_anOrderItemCannotBeAccepted() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Item drinkItem = new Item();
        drinkItem.setItemType(ItemType.DRINK);
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, order, OrderStatus.PENDING, drinkItem, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("DRINK order item #2 cannot be accepted by cook.\n", result.getMessage());
        assertSame(cook, orderItem1.getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(eq(orderItem1));
        verify(orderItemRepository, never()).save(eq(orderItem2));
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotFound() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.empty());
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #2 not found.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), eq(1L));
        verify(orderItemRepository, never()).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), eq(2L));
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotPending() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, order, OrderStatus.IN_PROGRESS, foodItem, cook, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #2 is not pending.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), eq(1L));
        verify(orderItemRepository, never()).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), eq(2L));
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void declineOrderItems_anOrderItemCannotBeDeclined() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Item drinkItem = new Item();
        drinkItem.setItemType(ItemType.DRINK);
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, order, OrderStatus.PENDING, drinkItem, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("DRINK order item #2 cannot be declined by cook.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), eq(1L));
        verify(orderItemRepository, never()).setStatusForOrderItem(eq(OrderStatus.DECLINED.ordinal()), eq(2L));
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void prepareOrderItems_anOrderItemIsNotFound() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.IN_PROGRESS, foodItem, cook, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.empty());
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #2 not found.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), eq(1L));
        verify(orderItemRepository, never()).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), eq(2L));
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void prepareOrderItems_anOrderItemIsNotInProgress() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.IN_PROGRESS, foodItem, cook, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, order, OrderStatus.PENDING, foodItem, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #2 is not in progress.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), eq(1L));
        verify(orderItemRepository, never()).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), eq(2L));
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void prepareOrderItems_anOrderItemCannotBePrepared() {
        Item foodItem = new Item();
        foodItem.setItemType(ItemType.FOOD);
        Item drinkItem = new Item();
        drinkItem.setItemType(ItemType.DRINK);
        Order order = new Order();
        Cook cook = new Cook();
        Barman barman = new Barman();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.IN_PROGRESS, foodItem, cook, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, order, OrderStatus.IN_PROGRESS, drinkItem, null, barman);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("DRINK order item #2 cannot be prepared by cook.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), eq(1L));
        verify(orderItemRepository, never()).setStatusForOrderItem(eq(OrderStatus.READY.ordinal()), eq(2L));
        verify(notificationService, times(1)).createNotification(anyString(), any(NotificationType.class), any(Order.class));
    }

    @Test
    public void cancelOrderItems_anOrderItemIsNotFound() {
        Item foodItem = new Item();
        Order order = new Order();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.empty());
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<Long>> result = orderItemService.cancelOrderItems(List.of(2L, 1L));

        assertEquals(List.of(1L), result.getData());
        assertEquals("Order item #2 not found.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).deleteById(eq(1L));
        verify(orderItemRepository, never()).deleteById(eq(2L));
    }

    @Test
    public void cancelOrderItems_anOrderItemCannotBeCancelled() {
        Item foodItem = new Item();
        Order order = new Order();
        Cook cook = new Cook();
        OrderItem orderItem1 = new OrderItem(1L, 1, order, OrderStatus.PENDING, foodItem, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, order, OrderStatus.IN_PROGRESS, foodItem, cook, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<Long>> result = orderItemService.cancelOrderItems(List.of(2L, 1L));

        assertEquals(List.of(1L), result.getData());
        assertEquals("Order item #2 cannot be cancelled.\n", result.getMessage());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).deleteById(eq(1L));
        verify(orderItemRepository, never()).deleteById(eq(2L));
    }
}
