package com.restaurant.backend.repository;

import com.restaurant.backend.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "UPDATE order_item oi SET oi.status = ?1 WHERE oi.id = ?2", nativeQuery = true)
    void setStatusForOrderItem(Integer status, Long id);
}
