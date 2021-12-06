package com.restaurant.backend.service.integration;

import com.restaurant.backend.domain.Cook;
import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.dto.responses.DataWithMessage;
import com.restaurant.backend.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:order_item_service_integration.sql")
@Transactional
public class OrderItemServiceIntegrationTests {
    @Autowired
    private OrderItemService orderItemService;

    @Test
    public void acceptOrderItems_anOrderItemIsNotFound() {
        Cook cook = new Cook();
        cook.setId(1L);
        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, List.of(9L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #9 not found.\n", result.getMessage());
        assertSame(cook, result.getData().get(0).getCook());
    }

    @Test
    public void acceptOrderItems_anOrderItemIsNotPending() {
        Cook cook = new Cook();
        cook.setId(1L);
        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, List.of(5L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #5 is not pending.\n", result.getMessage());
        assertSame(cook, result.getData().get(0).getCook());
    }

    @Test
    public void acceptOrderItems_anOrderItemCannotBeAccepted() {
        Cook cook = new Cook();
        cook.setId(1L);
        DataWithMessage<List<OrderItem>> result = orderItemService.acceptOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("DRINK order item #2 cannot be accepted by cook.\n", result.getMessage());
        assertSame(cook, result.getData().get(0).getCook());
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotFound() {
        Cook cook = new Cook();
        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, List.of(9L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #9 not found.\n", result.getMessage());
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotPending() {
        Cook cook = new Cook();
        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, List.of(5L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("Order item #5 is not pending.\n", result.getMessage());
    }

    @Test
    public void declineOrderItems_anOrderItemCannotBeDeclined() {
        Cook cook = new Cook();
        DataWithMessage<List<OrderItem>> result = orderItemService.declineOrderItems(cook, List.of(2L, 1L));

        assertEquals(1, result.getData().size());
        assertEquals(1L, result.getData().get(0).getId());
        assertEquals("DRINK order item #2 cannot be declined by cook.\n", result.getMessage());
    }

    @Test
    public void prepareOrderItems_anOrderItemIsNotFound() {
        Cook cook = new Cook();
        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, List.of(9L, 5L));

        assertEquals(1, result.getData().size());
        assertEquals(5L, result.getData().get(0).getId());
        assertEquals("Order item #9 not found.\n", result.getMessage());
    }

    @Test
    public void prepareOrderItems_anOrderItemIsNotInProgress() {
        Cook cook = new Cook();
        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, List.of(1L, 5L));

        assertEquals(1, result.getData().size());
        assertEquals(5L, result.getData().get(0).getId());
        assertEquals("Order item #1 is not in progress.\n", result.getMessage());
    }

    @Test
    public void prepareOrderItems_anOrderItemCannotBePrepared() {
        Cook cook = new Cook();
        DataWithMessage<List<OrderItem>> result = orderItemService.prepareOrderItems(cook, List.of(6L, 5L));

        assertEquals(1, result.getData().size());
        assertEquals(5L, result.getData().get(0).getId());
        assertEquals("DRINK order item #6 cannot be prepared by cook.\n", result.getMessage());
    }

    @Test
    public void cancelOrderItems_anOrderItemIsNotFound() {
        DataWithMessage<List<Long>> result = orderItemService.cancelOrderItems(List.of(9L, 1L));

        assertEquals(List.of(1L), result.getData());
        assertEquals("Order item #9 not found.\n", result.getMessage());
    }

    @Test
    public void cancelOrderItems_anOrderItemCannotBeCancelled() {
        DataWithMessage<List<Long>> result = orderItemService.cancelOrderItems(List.of(5L, 1L));

        assertEquals(List.of(1L), result.getData());
        assertEquals("Order item #5 cannot be cancelled.\n", result.getMessage());
    }
}
