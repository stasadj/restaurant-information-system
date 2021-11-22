package com.restaurant.backend.support;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class DateHelper {
    public static boolean isDateBetween(LocalDateTime dateTime, LocalDate from, LocalDate to) {
        return dateTime.isAfter(from.atStartOfDay()) && dateTime.isBefore(to.atStartOfDay());
    }
}
