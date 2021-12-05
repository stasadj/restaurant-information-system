package com.restaurant.backend.repository.integration;

import javax.transaction.Transactional;

import com.restaurant.backend.repository.OrderRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class OrderRecordRepositoryIntegrationTest {
    @Autowired
    private OrderRecordRepository orderRecordRepository;
}
