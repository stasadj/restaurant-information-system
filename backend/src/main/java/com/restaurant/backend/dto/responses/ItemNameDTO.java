package com.restaurant.backend.dto.responses;

import com.restaurant.backend.domain.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemNameDTO {
    private String name;
    private ItemType type;
}
