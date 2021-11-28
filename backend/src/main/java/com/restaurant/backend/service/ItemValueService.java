package com.restaurant.backend.service;

import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.repository.ItemValueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ItemValueService {
    private final ItemValueRepository itemValueRepository;

    public ItemValue create(ItemValue itemValue) {
        itemValue.setId(null);  // preventing update
        return itemValueRepository.save(itemValue);
    }
}
