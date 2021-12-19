package com.restaurant.backend.service.integration;

import static com.restaurant.backend.constants.ItemServiceTestConstants.EXISTENT_ITEM;
import static com.restaurant.backend.constants.ItemServiceTestConstants.ITEM_DESCRIPTION;
import static com.restaurant.backend.constants.ItemServiceTestConstants.ITEM_NAME;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NEW_ITEM_VALUE_DTO;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_CATEGORY_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_ITEM_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_TAG_ID;
import static com.restaurant.backend.constants.ItemServiceTestConstants.VALID_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.TagDTO;
import com.restaurant.backend.exception.CustomConstraintViolationException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.service.ItemService;
import com.restaurant.backend.support.ItemMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:item_service_integration.sql")
@Transactional
public class ItemServiceIntegrationTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

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

        Item createdItem = itemService.create(itemMapper.convert(VALID_ITEM));
        assertEquals(createdItem.getName(), ITEM_NAME);
        assertEquals(createdItem.getDescription(), ITEM_DESCRIPTION);
        assertEquals(createdItem.getItemValues().size(), VALID_ITEM.getItemValues().size());
        assertNotNull(createdItem.getId());
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
        String VALUE_FOR_CONCAT = "ABCCC";
        updatedItemDTO.setName(updatedItemDTO.getName() + VALUE_FOR_CONCAT);

        Item savedItem = itemService.editItem(updatedItemDTO);
        assertEquals(updatedItemDTO.getName(), savedItem.getName());

    }


    @Test
    public void updateItem_missingId() {

        CustomConstraintViolationException thrown = assertThrows(CustomConstraintViolationException.class, () -> {
            itemService.editItem(itemMapper.convert(VALID_ITEM));
        }, "CustomConstraintViolationException was expected");

    }


    @Test
    public void updateItem_invalidCategory() {

        ItemDTO existentItemDTO = itemMapper.convert(EXISTENT_ITEM);
        existentItemDTO.getCategory().setId(NONEXISTENT_CATEGORY_ID);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.editItem(existentItemDTO);
        }, "NotFoundException was expected");

        assertEquals(String.format("No category with id %d has been found",
                NONEXISTENT_CATEGORY_ID), thrown.getMessage());

    }

    @Test
    public void updateItem_invalidTags() {

        ItemDTO existentItemDTO = itemMapper.convert(EXISTENT_ITEM);

        existentItemDTO.setTags(new ArrayList<>() {
            {
                add(new TagDTO(NONEXISTENT_TAG_ID, null));
            }
        });

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.editItem(existentItemDTO);
        }, "NotFoundException was expected");

        assertEquals(String.format("No tag with id %d has been found",
                NONEXISTENT_TAG_ID), thrown.getMessage());
    }

    @Test
    public void changePrice() {

        ItemValue updatedValue = itemService.changeItemPrice(NEW_ITEM_VALUE_DTO);
        assertEquals(updatedValue.getPurchasePrice(), NEW_ITEM_VALUE_DTO.getPurchasePrice());
    }

}
