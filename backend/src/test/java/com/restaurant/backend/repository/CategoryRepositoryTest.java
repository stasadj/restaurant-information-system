package com.restaurant.backend.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:item_service_integration.sql")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @ParameterizedTest
    @MethodSource("data")
    public void testTimesUsed(long categoryId, int times) {
        int result = categoryRepository.timesUsed(categoryId);
        assertEquals(times, result);
    }

    private static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 1L, 0 },
                { 2L, 3 },
                { 3L, 0 },
                { 4L, 2 }
        });
    }
}
