
package com.restaurant.backend.service.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Category;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.service.CategoryService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
// @ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:item_service_integration.sql")
@Transactional
public class CategoryServiceIntegrationTests {

    @Autowired
    private CategoryService categoryService;


    @Test
    public void findOne_successful() {
        Category category = categoryService.findOne(1L);
        assertEquals(category.getName(), "appetizer");
        assertEquals(category.getId(), 1L);

    }

    @Test
    public void findOne_invalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            categoryService.findOne(1111L);
        }, "NotFoundException was expected");

        assertEquals(String.format("No category with id %d has been found",
                1111L), thrown.getMessage());
    }

}