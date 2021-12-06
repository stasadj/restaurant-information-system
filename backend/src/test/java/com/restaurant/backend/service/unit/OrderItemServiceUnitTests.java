package com.restaurant.backend.service.unit;

import com.restaurant.backend.domain.Order;
import com.restaurant.backend.domain.OrderItem;
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

import static com.restaurant.backend.constants.OrderItemServiceTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderItemServiceUnitTests {
    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private OrderItemService orderItemService;

    @Test
    public void acceptOrderItems_anOrderItemIsNotFound() {
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.empty());
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(A_COOK, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #2 not found.\n", result.getMessage());
        assertSame(A_COOK, orderItem1.getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void acceptOrderItems_anOrderItemIsNotPending() {
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, AN_ORDER, OrderStatus.IN_PROGRESS, A_FOOD_ITEM, A_COOK, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(A_COOK, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #2 is not pending.\n", result.getMessage());
        assertSame(A_COOK, orderItem1.getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(eq(orderItem1));
        verify(orderItemRepository, never()).save(eq(orderItem2));
    }

    @Test
    public void acceptOrderItems_anOrderItemCannotBeAccepted() {
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, AN_ORDER, OrderStatus.PENDING, A_DRINK_ITEM, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(A_COOK, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("DRINK order item #2 cannot be accepted by cook.\n", result.getMessage());
        assertSame(A_COOK, orderItem1.getCook());

        verify(orderItemRepository, times(2)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(eq(orderItem1));
        verify(orderItemRepository, never()).save(eq(orderItem2));
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotFound() {
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.empty());
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(A_COOK, List.of(2L, 1L));

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
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, AN_ORDER, OrderStatus.IN_PROGRESS, A_FOOD_ITEM, A_COOK, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(null);

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(A_COOK, List.of(2L, 1L));

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
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, AN_ORDER, OrderStatus.PENDING, A_DRINK_ITEM, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(A_COOK, List.of(2L, 1L));

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
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.IN_PROGRESS, A_FOOD_ITEM, A_COOK, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.empty());
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(A_COOK, List.of(2L, 1L));

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
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.IN_PROGRESS, A_FOOD_ITEM, A_COOK, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(A_COOK, List.of(2L, 1L));

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
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.IN_PROGRESS, A_FOOD_ITEM, A_COOK, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, AN_ORDER, OrderStatus.IN_PROGRESS, A_DRINK_ITEM, null, A_BARMAN);

        when(orderItemRepository.findById(eq(2L))).thenReturn(Optional.of(orderItem2));
        when(orderItemRepository.findById(eq(1L))).thenReturn(Optional.of(orderItem1));

        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(A_COOK, List.of(2L, 1L));

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
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);

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
        OrderItem orderItem1 = new OrderItem(1L, 1, AN_ORDER, OrderStatus.PENDING, A_FOOD_ITEM, null, null);
        OrderItem orderItem2 = new OrderItem(2L, 1, AN_ORDER, OrderStatus.IN_PROGRESS, A_FOOD_ITEM, A_COOK, null);

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
