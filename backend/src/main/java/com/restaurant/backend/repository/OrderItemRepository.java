package com.restaurant.backend.repository;

import com.restaurant.backend.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Modifying
    @Query(value = "UPDATE order_item oi SET oi.status = :status WHERE oi.id = :id", nativeQuery = true)
    void setStatusForOrderItem(@Param("status") Integer status, @Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE order_item oi SET oi.amount = :amount WHERE oi.id = :id", nativeQuery = true)
    void updateAmount(@Param("id") Long id, @Param("amount") Integer amount);
}
