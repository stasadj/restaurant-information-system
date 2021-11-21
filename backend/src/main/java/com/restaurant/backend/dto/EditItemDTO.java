package com.restaurant.backend.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.enums.ItemType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditItemDTO {

    @NotNull(message = "Item ID is missing.")
    protected Long id;

    @NotBlank(message = "Item name must not be blank.")
    protected String name;

    @NotNull(message = "Item category is missing.")
    protected CategoryDTO category;

    @NotBlank(message = "Item description must not be blank.")
    protected String description;

    protected String imageURL;

    protected List<TagDTO> tags;

    @NotNull(message = "Item is in menu info is missing.")
    protected Boolean inMenu;

    @NotNull(message = "Item initial value is missing.")
    @Valid
    protected ItemValueDTO currentItemValue;

    @NotNull(message = "Item type is missing.")
    protected ItemType itemType;

    protected Boolean deleted;

    public static Item toObject(EditItemDTO dto) {
        //TODO ObjectMapper
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setImageURL(dto.getImageURL());
        item.setInMenu(dto.getInMenu());
        item.setItemType(dto.getItemType());
        item.setDeleted(dto.getDeleted());

        item.setCategory(CategoryDTO.toObject(dto.getCategory()));

        item.setTags(new ArrayList<>());
        dto.getTags().forEach(tag -> item.getTags().add(TagDTO.toObject(tag)));

        item.setItemValues(new ArrayList<>());
        item.getItemValues().add(ItemValueDTO.toObject(dto.getCurrentItemValue()));

        return item;

    }

}
