package com.restaurant.backend.service;

import com.restaurant.backend.domain.Notification;
import com.restaurant.backend.domain.Order;
import com.restaurant.backend.domain.enums.NotificationType;
import com.restaurant.backend.dto.responses.NotificationDTO;
import com.restaurant.backend.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void deleteAll(List<Notification> notifications) {
        notificationRepository.deleteAll(notifications);
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public void createNotification(String text, NotificationType type, Order order) {
        Notification notification = new Notification();
        notification.setText(text);
        notification.setDeleted(false);
        notification.setOrder(order);
        notification.setType(type);
        notification.setDateTime(LocalDateTime.now());
        notificationRepository.save(notification);

        switch (type) {
            case WAITER_COOK:
                messagingTemplate.convertAndSend("/topic/cook", new NotificationDTO(notification));
                break;
            case WAITER_BARMAN:
                messagingTemplate.convertAndSend("/topic/barman", new NotificationDTO(notification));
                break;
            case COOK_WAITER:
            case BARMAN_WAITER:
                messagingTemplate.convertAndSendToUser(order.getWaiter().getUsername(),
                        "/queue/waiter", new NotificationDTO(notification));
                break;
            default:
                break;
        }
    }
}
