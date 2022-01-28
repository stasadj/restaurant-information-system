package com.restaurant.backend.support;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.stream.Collectors;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.dto.CategoryDTO;
import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.ItemValueDTO;
import com.restaurant.backend.dto.TagDTO;
import com.restaurant.backend.service.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import org.apache.commons.io.FileUtils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ItemMapper extends GenericObjectMapper<Item, ItemDTO> {

    @Autowired
    private StorageService storageService;

    @Override
    public ItemDTO convert(Item source) {
        ItemDTO dto = new ItemDTO();
        dto.setId(source.getId());
        dto.setName(source.getName());
        dto.setDescription(source.getDescription());
        dto.setImageFileName(source.getImageURL());
        dto.setInMenu(source.getInMenu());
        dto.setItemType(source.getItemType());
        dto.setDeleted(source.getDeleted());
        dto.setCategory(new CategoryDTO(source.getCategory()));
        dto.setTags(source.getTags().stream().map(TagDTO::new).collect(Collectors.toList()));
        dto.setCurrentItemValue(new ItemValueDTO(source.getItemValueAt(LocalDateTime.now())));

        //converting image from file system to Base64 
        try {
            String imageUrl = source.getImageURL() == null ? "default_image.png" : source.getImageURL();
            Resource resource = this.storageService.loadAsResource(imageUrl);
            if (resource != null) {
                File imageFile = resource.getFile();
                byte[] fileContent = FileUtils.readFileToByteArray(imageFile);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                dto.setImageBase64(encodedString);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dto;
    }

    public Item convertToDomain(ItemDTO dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setImageURL(dto.getImageFileName());
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
