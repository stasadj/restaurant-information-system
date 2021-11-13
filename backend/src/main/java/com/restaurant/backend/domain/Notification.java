package com.restaurant.backend.domain;

import com.restaurant.backend.domain.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "text", nullable = false)
    protected String text;

    @Column(name = "date_time", nullable = false)
    protected LocalDateTime dateTime;

    @Column(name = "type", nullable = false)
    protected NotificationType type;

    @Column(name = "deleted", nullable = false)
    protected Boolean deleted;
}
