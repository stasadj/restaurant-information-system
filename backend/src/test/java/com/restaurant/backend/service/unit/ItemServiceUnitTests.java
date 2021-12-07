
package com.restaurant.backend.service.unit;

import static com.restaurant.backend.constants.ItemServiceTestConstants.CATEGORY_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.EXISTENT_ITEM;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NEW_ITEM_VALUE;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NEW_ITEM_VALUE_DTO;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_CATEGORY_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_ITEM_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_TAG_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NULL_ID;
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
import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.repository.ItemValueRepository;
import com.restaurant.backend.service.CategoryService;
import com.restaurant.backend.service.ItemService;
import com.restaurant.backend.service.ItemValueService;
import com.restaurant.backend.service.TagService;

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
2. naming convention

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

        when(itemRepository.save(EXISTENT_ITEM))
                .thenReturn(EXISTENT_ITEM);

        when(itemValueRepository.save(any(ItemValue.class)))
                .thenReturn(NEW_ITEM_VALUE);

    }

    @Test
    public void getAll() {
        List<Item> found = itemService.getAll();

        verify(itemRepository, times(1)).findAll();
    }

    @Test
    public void getAllMenuItems() {
        List<Item> found = itemService.getAllMenuItems();

        verify(itemRepository, times(1)).findByInMenuTrue();
    }

    @Test
    public void findOne_invalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.findOne(NONEXISTENT_ITEM_ID);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found",
                NONEXISTENT_ITEM_ID), thrown.getMessage());
    }

    @Test
    public void createItem() {

        itemService.create(VALID_ITEM);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void createItem_invalidCategory() {

        Item item = new Item(VALID_ITEM);
        item.getCategory().setId(NONEXISTENT_CATEGORY_ID);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.create(item);
        }, "NotFoundException was expected");

        assertEquals(String.format("No category with id %d has been found",
                NONEXISTENT_CATEGORY_ID), thrown.getMessage());

    }

    @Test
    public void createItem_invalidTags() {

        Item item = new Item(VALID_ITEM);

        item.setTags(new ArrayList<>() {
            {
                add(new Tag(NONEXISTENT_TAG_ID, null));
            }
        });

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.create(item);
        }, "NotFoundException was expected");

        assertEquals(String.format("No tag with id %d has been found",
                NONEXISTENT_TAG_ID), thrown.getMessage());
    }

    @Test
    public void updateItem() {

        Item updatedItem = new Item(EXISTENT_ITEM);
        String VALUE_FOR_CONCAT = "ABC";
        updatedItem.setName(updatedItem.getName() + VALUE_FOR_CONCAT);
        updatedItem.setDescription(updatedItem.getDescription() + VALUE_FOR_CONCAT);
        // updatedItem.setImageURL(); //TODO write separate tests for image upload
        updatedItem.setItemType(ItemType.DRINK);

        Item savedItem = itemService.editItem(updatedItem);
        verify(itemRepository, times(1)).save(any(Item.class));
        assertEquals(updatedItem.getName(), savedItem.getName());
    }

    @Test
    public void updateItem_missingId() {

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.editItem(VALID_ITEM);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found",
                NULL_ID), thrown.getMessage());
    }

    @Test
    public void updateItem_invalidCategory() {

        Item item = new Item(EXISTENT_ITEM);
        item.getCategory().setId(NONEXISTENT_CATEGORY_ID);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.editItem(item);
        }, "NotFoundException was expected");

        assertEquals(String.format("No category with id %d has been found",
                NONEXISTENT_CATEGORY_ID), thrown.getMessage());

    }

    @Test
    public void updateItem_invalidTags() {

        Item item = new Item(EXISTENT_ITEM);

        item.setTags(new ArrayList<>() {
            {
                add(new Tag(NONEXISTENT_TAG_ID, null));
            }
        });

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.editItem(item);
        }, "NotFoundException was expected");

        assertEquals(String.format("No tag with id %d has been found",
                NONEXISTENT_TAG_ID), thrown.getMessage());
    }

    @Test
    public void changePrice() {

        ItemValue updatedValue = itemService.changeItemPrice(NEW_ITEM_VALUE_DTO);
        assertEquals(updatedValue.getPurchasePrice(), NEW_ITEM_VALUE_DTO.getPurchasePrice());
    }

    //TODO add tests for only changing selling price, test for adding future date etc.

}
