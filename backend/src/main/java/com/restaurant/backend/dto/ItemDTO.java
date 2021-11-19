package com.restaurant.backend.dto;

import java.util.List;

import com.restaurant.backend.domain.enums.ItemType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {

    protected Long id;

    protected String name;

    protected CategoryDTO category;

    protected String description;

    protected String imageURL;

    protected List<TagDTO> tags;

    protected Boolean inMenu;

    protected List<ItemValueDTO> itemValues;

    protected ItemType itemType;

    protected Boolean deleted;
}
