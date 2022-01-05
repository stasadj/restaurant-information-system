package com.restaurant.backend.dto.table;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    @NotNull(message = "Room id must be present")
    private String id;

    @NotNull(message = "Tables list must be present")
    private List<@Valid TableDTO> tables;
}
