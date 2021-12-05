package com.restaurant.backend.dto.responses;

import com.restaurant.backend.domain.Notification;
import com.restaurant.backend.domain.enums.NotificationType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationDTO {
    protected Long id;
    protected String text;
    protected LocalDateTime dateTime;
    protected NotificationType type;
    protected Long orderId;

    public NotificationDTO(Notification notification) {
        id = notification.getId();
        text = notification.getText();
        dateTime = notification.getDateTime();
        type = notification.getType();
        orderId = notification.getOrder().getId();
    }
}
