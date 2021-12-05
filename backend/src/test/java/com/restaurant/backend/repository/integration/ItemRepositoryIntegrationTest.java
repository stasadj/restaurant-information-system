package com.restaurant.backend.repository.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.repository.ItemRepository;

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
public class ItemRepositoryIntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    
}
