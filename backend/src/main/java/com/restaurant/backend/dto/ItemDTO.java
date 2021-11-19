package com.restaurant.backend.dto;

import java.util.ArrayList;
import java.util.List;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.Tag;
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

    protected ItemValueDTO currentItemValue;

    protected ItemType itemType;

    protected Boolean deleted;

    public static Item toObject(ItemDTO dto) {

        Item item = new Item();
        item.setId(dto.getId());
        item.setDescription(dto.getDescription());
        item.setImageURL(dto.getImageURL());
        item.setInMenu(dto.getInMenu());
        item.setItemType(dto.getItemType());
        item.setDeleted(dto.getDeleted());

        item.setCategory(CategoryDTO.toObject(dto.getCategory()));

        item.setTags(new ArrayList<Tag>());
        dto.getTags().forEach(tag -> item.getTags().add(TagDTO.toObject(tag)));

        return item;

    }
}
