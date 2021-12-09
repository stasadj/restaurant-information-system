package com.restaurant.backend.repository;

import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.domain.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:order_item_service_integration.sql")
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void testSetStatus() {
        orderItemRepository.setStatusForOrderItem(OrderStatus.IN_PROGRESS.ordinal(), 1L);
        Optional<OrderItem> orderItem = orderItemRepository.findById(1L);
        assertTrue(orderItem.isPresent());
        assertEquals(OrderStatus.IN_PROGRESS, orderItem.get().getOrderStatus());
    }

    @Test
    public void testUpdateAmount() {
        orderItemRepository.updateAmount(1L, 4);
        Optional<OrderItem> orderItem = orderItemRepository.findById(1L);
        assertTrue(orderItem.isPresent());
        assertEquals(4, orderItem.get().getAmount());
    }
}
