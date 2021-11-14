package com.restaurant.backend.repository;

import com.restaurant.backend.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder_IdEquals(Long orderId);
}
