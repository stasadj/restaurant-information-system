package com.restaurant.backend.repository;

import com.restaurant.backend.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
