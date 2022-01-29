
package com.restaurant.backend.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Category;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.CategoryRepository;
import com.restaurant.backend.service.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@Transactional
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class CategoryServiceUnitTests {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService vategoryService;

    private final Category EXISTING_CATEGORY = new Category(1l, "appetizer");

    @BeforeEach
    public void setup() {

        when(categoryRepository.findById(eq(EXISTING_CATEGORY.getId())))
                .thenReturn(Optional.of(EXISTING_CATEGORY));

        when(categoryRepository.findById(eq(1111L)))
                .thenThrow(new NotFoundException(String.format("No category with id %d has been found",
                        1111L)));

        when(categoryRepository.save(eq(EXISTING_CATEGORY)))
                .thenReturn(EXISTING_CATEGORY);

    }

    @Test
    public void findOne_successful() {
        Category category = vategoryService.findOne(EXISTING_CATEGORY.getId());
        assertEquals(category.getName(), "appetizer");
        assertEquals(category.getId(), 1L);

    }

    @Test
    public void findOne_invalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            vategoryService.findOne(1111L);
        }, "NotFoundException was expected");

        assertEquals(String.format("No category with id %d has been found",
                1111L), thrown.getMessage());
    }

}
