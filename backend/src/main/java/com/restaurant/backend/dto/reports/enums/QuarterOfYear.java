package com.restaurant.backend.dto.reports.enums;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;

import java.time.LocalDate;
import java.util.Objects;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum QuarterOfYear {
    Q1("First Quarter"), Q2("Second Quarter"), Q3("Third Quarter"), Q4("Fourth Quarter");

    public final String label;

    public Integer getValue() {
        switch (this) {
        case Q1:
            return 1;
        case Q2:
            return 2;
        case Q3:
            return 3;
        case Q4:
            return 4;
        default:
            return -1;
        }
    }

    public static QuarterOfYear from(LocalDate date) {
        date = Objects.requireNonNull(date, "Date must not be null");
        int quarter = date.get(QUARTER_OF_YEAR);
        return of(quarter);
    }

    public static QuarterOfYear of(int quarter) throws IllegalArgumentException {
        switch (quarter) {
        case 1:
            return Q1;
        case 2:
            return Q2;
        case 3:
            return Q3;
        case 4:
            return Q4;
        default:
            throw new IllegalArgumentException("Quarter cannot be lower than 1 or greater than 4");
        }
    }
}
