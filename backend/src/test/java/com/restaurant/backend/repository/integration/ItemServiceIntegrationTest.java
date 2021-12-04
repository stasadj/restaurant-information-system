package com.restaurant.backend.repository.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;



/*
 Todo: 
 1. Test method naming convention
 2. Add more asserts in methods
 3. Create common file for constants
 4. Add tests for the rest of service methods
 */

@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void testGetAll() {
        List<Item> found = itemService.getAll();
        found.forEach(item -> assertTrue(!item.getDeleted()));
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

    // @Test
    // public void testGetAlByCategory() {
    // List<Item> found = itemService.getAllByCategory(1L);
    // }

    @Test
    public void testFindOne() {
        Item item = itemService.findOne(1L);
        assertEquals(item.getName(), "Spaghetti carbonara");
    }

    @Test
    public void testFindOne_InvalidId() {
        assertThrows(NotFoundException.class, () -> itemService.findOne(111111L));
    }

    @Test
    public void testAddToMenu() {
        Item item = itemService.addToMenu(3L);
        assertTrue(item.getInMenu());
    }

    @Test
    public void testRemoveFromMenu() {
        Item item = itemService.removeFromMenu(3L);
        assertTrue(!item.getInMenu());
    }

    // @Test
    // public void testDelete() {
    //     itemService.delete(1L);
    // }
}
