package com.restaurant.backend.dto.table;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class TableOrganizationDTO {
    @NotNull(message = "Tables list must be present")
    private List<TableDTO> tables;

    @NotNull(message = "Rooms list must be present")
    private List<String> rooms;
}
