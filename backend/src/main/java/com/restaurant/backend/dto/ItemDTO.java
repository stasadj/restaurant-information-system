package com.restaurant.backend.dto;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.validation.interfaces.CreateInfo;
import com.restaurant.backend.validation.interfaces.EditInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {

    @NotNull(message = "Id cannot be null.", groups = EditInfo.class)
    @Null(message = "Id should be null.", groups = CreateInfo.class)
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

    public static Item toDomain(ItemDTO dto) {
        //TODO ObjectMapper
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setImageURL(dto.getImageURL());
        item.setInMenu(dto.getInMenu());
        item.setItemType(dto.getItemType());
        item.setDeleted(dto.getDeleted());

        item.setCategory(CategoryDTO.toDomain(dto.getCategory()));

        item.setTags(new ArrayList<>());
        dto.getTags().forEach(tag -> item.getTags().add(TagDTO.toDomain(tag)));

        item.setItemValues(new ArrayList<>());
        item.getItemValues().add(ItemValueDTO.toDomain(dto.getCurrentItemValue()));

        return item;
    }

    public ItemDTO(Item item) {
        //TODO ObjectMapper
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.imageURL = item.getImageURL();
        this.inMenu = item.getInMenu();
        this.itemType = item.getItemType();
        this.deleted = item.getDeleted();

        this.category = new CategoryDTO(item.getCategory());
        this.tags = new ArrayList<>();
        item.getTags().forEach(tag -> this.tags.add(new TagDTO(tag)));

        ItemValue currentValue = item.getItemValueAt(LocalDateTime.now());
        this.currentItemValue = new ItemValueDTO(currentValue);
    }
}
