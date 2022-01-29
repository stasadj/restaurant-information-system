
package com.restaurant.backend.service.unit;

import static com.restaurant.backend.constants.ItemServiceTestConstants.VALID_TAG1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.TagRepository;
import com.restaurant.backend.service.TagService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@Transactional
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class TagServiceUnitTests {

    @MockBean
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    private final Tag EXISTING_TAG = new Tag(1l, "vegan");

    @BeforeEach
    public void setup() {

        when(tagRepository.findById(eq(EXISTING_TAG.getId())))
                .thenReturn(Optional.of(EXISTING_TAG));

        when(tagRepository.findById(eq(1111L)))
                .thenThrow(new NotFoundException(String.format("No tag with id %d has been found",
                        1111L)));

        when(tagRepository.save(eq(EXISTING_TAG)))
                .thenReturn(EXISTING_TAG);

    }

    @Test
    public void findOne_successful() {
        Tag tag = tagService.findOne(EXISTING_TAG.getId());
        assertEquals(tag.getName(), "vegan");
        assertEquals(tag.getId(), 1L);

    }

    @Test
    public void findOne_invalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            tagService.findOne(1111L);
        }, "NotFoundException was expected");

        assertEquals(String.format("No tag with id %d has been found",
                1111L), thrown.getMessage());
    }

}
