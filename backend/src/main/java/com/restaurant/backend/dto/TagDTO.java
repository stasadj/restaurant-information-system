package com.restaurant.backend.dto;

import com.restaurant.backend.domain.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TagDTO {
    
    protected Long id;

    protected String name;

    public static Tag toObject(TagDTO dto){
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;

    }

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
