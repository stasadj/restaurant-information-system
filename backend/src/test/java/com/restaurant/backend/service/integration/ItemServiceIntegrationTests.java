package com.restaurant.backend.service.integration;

import static com.restaurant.backend.constants.ItemServiceTestConstants.EXISTENT_ITEM;
import static com.restaurant.backend.constants.ItemServiceTestConstants.ITEM_NAME;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NEW_ITEM_VALUE_DTO;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_CATEGORY_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_ITEM_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_TAG_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.VALID_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.service.ItemService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:item_service_integration.sql")
@Transactional
public class ItemServiceIntegrationTests {

    @Autowired
    private ItemService itemService;

    @Test
    public void findOne_successful() {
        Item item = itemService.findOne(1L);
        assertEquals(item.getName(), "Spaghetti carbonara");
        assertEquals(item.getDescription(), "Traditional Italian dish served with garlic, olive oil and parsley");
        assertTrue(item.getInMenu());

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
    public void getAll() {
        List<Item> found = itemService.getAll();
        found.forEach(item -> assertFalse(item.getDeleted()));
        assertEquals(4, found.size());
    }

    @Test
    public void getAllPlusDeleted() {
        List<Item> found = itemService.getAllPlusDeleted();
        assertEquals(5, found.size());
    }

    @Test
    public void getAllMenuItems() {
        List<Item> found = itemService.getAllMenuItems();
        found.forEach(item -> assertTrue(item.getInMenu()));
        found.forEach(item -> assertFalse(item.getDeleted()));
        assertEquals(3, found.size());
    }

    @Test
    public void addToMenu_successful() {
        Item item = itemService.addToMenu(3L);
        assertTrue(item.getInMenu());
    }

    @Test
    public void addToMenu_InvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.addToMenu(NONEXISTENT_ITEM_ID);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found", NONEXISTENT_ITEM_ID), thrown.getMessage());
    }

    @Test
    public void removeFromMenu_successful() {
        Item item = itemService.removeFromMenu(3L);
        assertFalse(item.getInMenu());
    }

    @Test
    public void removeFromMenu_InvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.removeFromMenu(NONEXISTENT_ITEM_ID);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found", NONEXISTENT_ITEM_ID), thrown.getMessage());
    }

    @Test
    public void deleteItem_successful() {
        itemService.delete(1L);
    }

    @Test
    public void deleteItem_unsuccessful() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.delete(NONEXISTENT_ITEM_ID);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found", NONEXISTENT_ITEM_ID), thrown.getMessage());
    }

    @Test
    public void createItem() {

        Item createdItem = itemService.create(VALID_ITEM);
        assertEquals(createdItem.getName(), ITEM_NAME);
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
        assertEquals(updatedItem.getName(), savedItem.getName());
    }

    // @Test
    // public void updateItem_missingId() {

    // NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
    // itemService.editItem(VALID_ITEM);
    // }, "NotFoundException was expected");

    // assertEquals(String.format("No item with id %d has been found",
    // NULL_ID), thrown.getMessage());
    // }

    @Test
    public void updateItem_invalidId() {

        Item edited = new Item(VALID_ITEM);
        edited.setId(NONEXISTENT_ITEM_ID);
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.editItem(edited);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found",
                NONEXISTENT_ITEM_ID), thrown.getMessage());
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
