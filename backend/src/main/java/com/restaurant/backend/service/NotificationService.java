package com.restaurant.backend.service;

import com.restaurant.backend.domain.Notification;
import com.restaurant.backend.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void deleteAll(List<Notification> notifications) {
        notificationRepository.deleteAll(notifications);
    }

    public Notification create() {
        // TODO implement
        return new Notification();
    }
}
