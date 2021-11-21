package com.restaurant.backend.dto.table;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TableDTO {
    @NotNull(message = "Table identifier is missing for table")
    @Positive(message = "Table identifier must be greater than 0")
    private Long id;

    @NotNull(message = "Room index is missing for table")
    private Long roomIndex;

    @NotNull(message = "xPos is missing for table")
    private BigDecimal xPos;

    @NotNull(message = "yPos is missing for table")
    private BigDecimal yPos;
}
