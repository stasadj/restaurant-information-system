package com.restaurant.backend.domain;

import com.restaurant.backend.domain.enums.NotificationType;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    protected Order order;
}
