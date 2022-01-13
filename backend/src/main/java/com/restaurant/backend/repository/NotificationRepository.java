package com.restaurant.backend.repository;

import com.restaurant.backend.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.order.waiter.id = :waiterId AND n.deleted = false")
    List<Notification> findAllForWaiter(Long waiterId);
}