
package com.restaurant.backend.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Category;
import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.service.CategoryService;
import com.restaurant.backend.service.ItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;


/*
Todo:
1. add constants 
2. naming convention
3. tests for rest of methods

*/
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class ItemServiceUnitTests {
    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @BeforeEach
    public void setup() {
        Category category = new Category();
        category.setId(2L);

        when(categoryService.findOne(eq(2L)))
                .thenReturn(category);

        List<Item> items = (new ArrayList<>() {
            {
                add(createValidItem());
                add(createValidItem());
                add(createValidItem());
            }
        });

        when(itemRepository.findAll())
                .thenReturn(items);
    }

    @Test
    public void createItem_successful() {

        Item item = createValidItem();
        itemService.create(item);
    }

    @Test
    public void testGetAll() {
        List<Item> found = itemService.getAll();

        verify(itemRepository, times(1)).findAll();
        assertEquals(3, found.size()); //todo change to common constant
    }

    private Item createValidItem() {
        Item item = new Item();
        item.setName("Potato salad");
        item.setDescription("Potato and egg salad dressed with vinegar with a side of red oniom");
        item.setImageURL(""); // TODO add test with correct and incorrect image urls when image upload is
                              // implemented
        item.setInMenu(true);
        item.setItemType(ItemType.FOOD);

        Category category = new Category();
        category.setId(2L);
        item.setCategory(category);

        ItemValue itemValue = new ItemValue();
        itemValue.setFromDate(LocalDateTime.now());
        itemValue.setPurchasePrice(new BigDecimal(500.5));
        itemValue.setSellingPrice(new BigDecimal(600.6));
        item.setItemValues(new ArrayList<>());

        item.setItemValues(new ArrayList<>() {
            {
                add(itemValue);
            }
        });

        item.setTags(new ArrayList<>());

        return item;
    }

   
}
