package com.restaurant.backend.service.integration;
import static com.restaurant.backend.constants.ItemServiceTestConstants.NONEXISTENT_ITEM_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.service.ItemService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/*
 Todo: 
 1. Test method naming convention
 2. Add more asserts in methods
 3. Create common file for constants and use them here
 4. Add tests for the rest of service methods
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:item_service_integration.sql")
@Transactional
public class ItemServiceIntegrationTests {

    @Autowired
    private ItemService itemService;

    @Test
    public void testGetAll() {
        List<Item> found = itemService.getAll();
        found.forEach(item -> assertFalse(item.getDeleted()));
        assertEquals(4, found.size()); //todo: add constants into common file
    }

    @Test
    public void testGetAllPlusDeleted() {
        List<Item> found = itemService.getAllPlusDeleted();
        assertEquals(5, found.size());
    }

    @Test
    public void testGetAllMenuItems() {
        List<Item> found = itemService.getAllMenuItems();
        found.forEach(item -> assertTrue(item.getInMenu()));
        found.forEach(item -> assertTrue(!item.getDeleted()));
        assertEquals(3, found.size());
    }

    //TODO: either delete this test and its method or implement both
    // @Test
    // public void testGetAlByCategory() {
    // List<Item> found = itemService.getAllByCategory(1L);
    // }

    @Test
    public void testFindOne_successful() {
        Item item = itemService.findOne(1L);
        assertEquals(item.getName(), "Spaghetti carbonara");
    }

    @Test
    public void testFindOne_InvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.findOne(NONEXISTENT_ITEM_ID);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found", NONEXISTENT_ITEM_ID), thrown.getMessage());
    }

    @Test
    public void testAddToMenu_successful() {
        Item item = itemService.addToMenu(3L);
        assertTrue(item.getInMenu());
    }

    @Test
    public void testAddToMenu_InvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            itemService.addToMenu(NONEXISTENT_ITEM_ID);
        }, "NotFoundException was expected");

        assertEquals(String.format("No item with id %d has been found", NONEXISTENT_ITEM_ID), thrown.getMessage());
    }

    @Test
    public void testRemoveFromMenu_successful() {
        Item item = itemService.removeFromMenu(3L);
        assertFalse(item.getInMenu());
    }

    @Test
    public void testRemoveFromMenu_InvalidId() {
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
}
