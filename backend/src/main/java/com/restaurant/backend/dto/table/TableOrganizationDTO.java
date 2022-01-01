package com.restaurant.backend.dto.table;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableOrganizationDTO {

    @NotNull
    private List<@Valid RoomDTO> rooms;
}
