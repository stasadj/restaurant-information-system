package com.restaurant.backend.repository;

import com.restaurant.backend.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByWaiter_Id(Long id);

    List<Order> findAllByTableIdIsNotNull();
}
