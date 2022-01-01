package com.restaurant.backend.dto.table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
    @NotNull(message = "Table identifier is missing for table")
    @Positive(message = "Table identifier must be greater than 0")
    private Integer id;

    @NotNull(message = "Rotate value is missing for table")
    private Integer rotateValue;

    @NotNull(message = "Size is missing for table")
    private TableSizeDTO size;

    @NotNull(message = "Radius is missing for table")
    private Integer radius;

    @NotNull(message = "Position is missing for table")
    private TablePositionDTO position;
}
