package com.restaurant.backend.support;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.dto.CategoryDTO;
import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.ItemValueDTO;
import com.restaurant.backend.dto.TagDTO;

import org.springframework.stereotype.Component;

@Component
public class ItemMapper extends GenericObjectMapper<Item, ItemDTO> {
    @Override
    public ItemDTO convert(Item source) {
        ItemDTO dto = new ItemDTO();
        dto.setId(source.getId());
        dto.setName(source.getName());
        dto.setDescription(source.getDescription());
        dto.setImageURL(source.getImageURL());
        dto.setInMenu(source.getInMenu());
        dto.setItemType(source.getItemType());
        dto.setDeleted(source.getDeleted());
        dto.setCategory(new CategoryDTO(source.getCategory()));
        dto.setTags(source.getTags().stream().map(TagDTO::new).collect(Collectors.toList()));
        dto.setCurrentItemValue(new ItemValueDTO(source.getItemValueAt(LocalDateTime.now())));
        return dto;
    }

    public Item convertToDomain(ItemDTO dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setImageURL(dto.getImageURL());
        item.setInMenu(dto.getInMenu());
        item.setItemType(dto.getItemType());
        item.setDeleted(dto.getDeleted());
        item.setCategory(CategoryDTO.toDomain(dto.getCategory()));
        item.setTags(dto.getTags().stream().map(TagDTO::toDomain).collect(Collectors.toList()));
        item.setItemValues(new ArrayList<>() {{
            add(ItemValueDTO.toDomain(dto.getCurrentItemValue()));
        }});
        return item;
    }
}
