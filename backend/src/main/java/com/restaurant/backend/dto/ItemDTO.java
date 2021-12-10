package com.restaurant.backend.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.validation.interfaces.CreateInfo;
import com.restaurant.backend.validation.interfaces.EditInfo;
import javax.validation.groups.Default;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {

    @NotNull(message = "Id cannot be null.", groups = {Default.class, EditInfo.class})
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
}
