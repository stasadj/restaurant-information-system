package com.restaurant.backend.support;

import java.time.LocalDate;

public abstract class DateHelper {
    public static boolean getIsDateBetween(LocalDate date, LocalDate from, LocalDate to) {
        return (date.equals(from) || date.isAfter(from)) && (date.equals(to) || date.isBefore(to));
    }
}
