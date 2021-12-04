package com.restaurant.backend.repository.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.transaction.Transactional;

import com.restaurant.backend.domain.OrderRecord;
import com.restaurant.backend.repository.OrderRecordRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
public class OrderRecordRepositoryIntegrationTest {
    @Autowired
    private OrderRecordRepository orderRecordRepository;

    @Test
    public void testExample() throws Exception {
        OrderRecord persistedRecord = orderRecordRepository.getById(1L);
        assertEquals(2, persistedRecord.getAmount());
    }
}
