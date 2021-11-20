package com.restaurant.backend.dto;

import com.restaurant.backend.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    
    protected Long id;

    protected String name;

    public static Category toObject(CategoryDTO dto){
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;

    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
