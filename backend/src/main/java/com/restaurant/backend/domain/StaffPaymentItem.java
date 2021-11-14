package com.restaurant.backend.domain;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff_payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffPaymentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "amount", nullable = false)
    protected BigDecimal amount;

    @Column(name = "date", nullable = false)
    protected LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    protected User user;
}
