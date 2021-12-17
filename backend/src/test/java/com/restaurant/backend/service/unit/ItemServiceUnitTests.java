
package com.restaurant.backend.service.unit;

import static com.restaurant.backend.constants.ItemServiceTestConstants.CATEGORY_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.EXISTENT_ITEM;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NEW_ITEM_VALUE;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NEW_ITEM_VALUE_DTO;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_CATEGORY_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_ITEM_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_TAG_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.TAG1_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.TAG2_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.VALID_CATEGORY;
import static com.restaurant.backend.constants.ItemServiceTestConstants.VALID_ITEM;
import static com.restaurant.backend.constants.ItemServiceTestConstants.VALID_ITEM_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.VALID_TAG1;
import static com.restaurant.backend.constants.ItemServiceTestConstants.VALID_TAG2;
import static com.restaurant.backend.constants.ItemServiceTestConstants.generateValidItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.TagDTO;
import com.restaurant.backend.exception.CustomConstraintViolationException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.repository.ItemValueRepository;
import com.restaurant.backend.service.CategoryService;
import com.restaurant.backend.service.ItemService;
import com.restaurant.backend.service.ItemValueService;
import com.restaurant.backend.service.TagService;
import com.restaurant.backend.support.ItemMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

/*
Todo:
1. Invalid img url tests

*/
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class ItemServiceUnitTests {
    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private TagService tagService;

    @Autowired
    private ItemService itemService;

    @SpyBean
    private ItemValueService itemValueService;

    @MockBean
    private ItemValueRepository itemValueRepository;

    @Autowired
    private ItemMapper itemMapper;

    @BeforeEach
    public void setup() {

        when(categoryService.findOne(eq(CATEGORY_ID)))
                .thenReturn(VALID_CATEGORY);

        when(categoryService.findOne(eq(NONEXISTENT_CATEGORY_ID)))
                .thenThrow(new NotFoundException(String.format("No category with id %d has been found",
                        NONEXISTENT_CATEGORY_ID)));

        when(tagService.findOne(eq(TAG1_ID)))
                .thenReturn(VALID_TAG1);

        when(tagService.findOne(eq(TAG2_ID)))
                .thenReturn(VALID_TAG2);

        when(tagService.findOne(eq(NONEXISTENT_TAG_ID)))
                .thenThrow(new NotFoundException(String.format("No tag with id %d has been found",
                        NONEXISTENT_TAG_ID)));

        List<Item> items = (new ArrayList<>() {
            {
                add(generateValidItem());
                add(generateValidItem());
                add(generateValidItem());
            }
        });

        when(itemRepository.findAll())
                .thenReturn(items);

        when(itemRepository.findByInMenuTrue())
                .thenReturn(items);

        when(itemRepository.findById(eq(VALID_ITEM_ID)))
                .thenReturn(Optional.of(EXISTENT_ITEM));

        when(itemRepository.findById(eq(NONEXISTENT_ITEM_ID)))
                .thenReturn(Optional.empty());

        when(itemRepository.findById(null))
                .thenReturn(Optional.empty());

        when(itemRepository.save(any(Item.class)))
                .thenReturn(EXISTENT_ITEM);

        when(itemValueRepository.save(any(ItemValue.class)))
                .thenReturn(NEW_ITEM_VALUE);

    }

    @Test
    public void createItem() {

        ItemDTO itemDTO = itemMapper.convert(VALID_ITEM);
        itemService.create(itemDTO);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void createItem_invalidCategory() {

        ItemDTO itemDTO = itemMapper.convert(VALID_ITEM);
        itemDTO.getCategory().setId(NONEXISTENT_CATEGORY_ID);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.create(itemDTO);
        }, "NotFoundException was expected");

        assertEquals(String.format("No category with id %d has been found",
                NONEXISTENT_CATEGORY_ID), thrown.getMessage());

    }

    @Test
    public void createItem_invalidTags() {

        ItemDTO itemDTO = itemMapper.convert(VALID_ITEM);

        itemDTO.setTags(new ArrayList<>() {
            {
                add(new TagDTO(NONEXISTENT_TAG_ID, null));
            }
        });

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.create(itemDTO);
        }, "NotFoundException was expected");

        assertEquals(String.format("No tag with id %d has been found",
                NONEXISTENT_TAG_ID), thrown.getMessage());
    }

    @Test
    public void updateItem() {

        ItemDTO updatedItemDTO = itemMapper.convert(EXISTENT_ITEM);
        String VALUE_FOR_CONCAT = "ABC";
        updatedItemDTO.setName(updatedItemDTO.getName() + VALUE_FOR_CONCAT);
        updatedItemDTO.setDescription(updatedItemDTO.getDescription() + VALUE_FOR_CONCAT);
        // updatedItem.setImageURL(); //TODO write separate tests for image upload
        updatedItemDTO.setItemType(ItemType.DRINK);

        Item savedItem = itemService.editItem(updatedItemDTO);
        verify(itemRepository, times(1)).save(any(Item.class));
        assertEquals(updatedItemDTO.getName(), savedItem.getName());
        assertEquals(updatedItemDTO.getDescription(), savedItem.getDescription());
        assertEquals(updatedItemDTO.getItemType(), savedItem.getItemType());
    }

    @Test
    public void updateItem_missingId() {

        CustomConstraintViolationException thrown = assertThrows(CustomConstraintViolationException.class, () -> {
            itemService.editItem(itemMapper.convert(VALID_ITEM));
        }, "CustomConstraintViolationException was expected");

    }

    @Test
    public void updateItem_invalidCategory() {

        ItemDTO existantItemDTO = itemMapper.convert(EXISTENT_ITEM);
        existantItemDTO.getCategory().setId(NONEXISTENT_CATEGORY_ID);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.editItem(existantItemDTO);
        }, "NotFoundException was expected");

        assertEquals(String.format("No category with id %d has been found",
                NONEXISTENT_CATEGORY_ID), thrown.getMessage());

    }

    @Test
    public void updateItem_invalidTags() {

        ItemDTO existantItemDTO = itemMapper.convert(EXISTENT_ITEM);

        existantItemDTO.setTags(new ArrayList<>() {
            {
                add(new TagDTO(NONEXISTENT_TAG_ID, null));
            }
        });

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.editItem(existantItemDTO);
        }, "NotFoundException was expected");

        assertEquals(String.format("No tag with id %d has been found",
                NONEXISTENT_TAG_ID), thrown.getMessage());
    }

    @Test
    public void deleteItem_unsuccessful() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.delete(NONEXISTENT_ITEM_ID);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found", NONEXISTENT_ITEM_ID), thrown.getMessage());
    }

    @Test
    public void changePrice() {

        ItemValue updatedValue = itemService.changeItemPrice(NEW_ITEM_VALUE_DTO);
        assertEquals(updatedValue.getPurchasePrice(), NEW_ITEM_VALUE_DTO.getPurchasePrice());
    }

}
