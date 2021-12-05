package com.restaurant.backend.service.integration;

import com.restaurant.backend.domain.Order;
import com.restaurant.backend.domain.OrderRecord;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:order_service_integration.sql")
@Transactional
public class OrderServiceIntegrationTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void create_tableHasAnOrder() {
        OrderItemDTO itemDTO = new OrderItemDTO(null, 2, null, OrderStatus.PENDING, 1L, null, null);
        OrderDTO orderDTO = new OrderDTO(null, null, "note", 1, Collections.singletonList(itemDTO), 1L);

        assertThrows(BadRequestException.class, () -> {
            orderService.create(orderDTO);
        }, "BadRequestException was expected");
    }

    @Test
    public void create() {
        OrderItemDTO itemDTO = new OrderItemDTO(null, 2, null, OrderStatus.PENDING, 1L, null, null);
        OrderDTO orderDTO = new OrderDTO(null, null, "note", 4, Collections.singletonList(itemDTO), 1L);

        List<Order> results = orderService.create(orderDTO);

        assertEquals(4, results.size());
        assertEquals(4, results.get(3).getTableId());
        assertEquals(1, results.get(3).getWaiter().getId());
        assertEquals(OrderStatus.PENDING, results.get(3).getOrderItems().get(0).getOrderStatus());
    }

    @Test
    public void editOrder() {
        OrderItemDTO itemDTO1 = new OrderItemDTO(4L, 2, 2L, OrderStatus.PENDING, 4L, null, null);
        OrderItemDTO itemDTO2 = new OrderItemDTO(null, 1, null, OrderStatus.PENDING, 1L, null, null);
        OrderDTO orderDTO = new OrderDTO(2L, null, "note edited", 2, Arrays.asList(itemDTO1, itemDTO2), 1L);

        List<Order> results = orderService.editOrder(orderDTO);

        assertEquals(3, results.size());
        assertEquals(2, results.get(1).getTableId());
        assertEquals(1, results.get(1).getWaiter().getId());
        assertEquals("note edited", results.get(1).getNote());
        assertEquals(2, results.get(1).getOrderItems().size());
        assertEquals(2, results.get(1).getOrderItems().get(0).getAmount());
        assertEquals(OrderStatus.PENDING, results.get(1).getOrderItems().get(1).getOrderStatus());
    }

    @Test
    public void finalizeOrder_orderItemNotReady() {
        assertThrows(BadRequestException.class, () -> {
            orderService.finalizeOrder(1L);
        }, "BadRequestException was expected");
    }

    @Test
    public void finalizeOrder() {
       List<OrderRecord> records = orderService.finalizeOrder(3L);
        assertEquals(1, records.size());
    }
}
