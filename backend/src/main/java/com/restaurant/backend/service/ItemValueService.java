package com.restaurant.backend.service;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.dto.ChangePriceDTO;
import com.restaurant.backend.repository.ItemValueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@AllArgsConstructor
public class ItemValueService {
    private ItemValueRepository repository;

    private ItemService itemService;

    public ItemValue changeItemPrice(ChangePriceDTO dto) {
        Item item = itemService.getById(dto.getItemId());
        ItemValue currentValue = item.getItemValueAt(LocalDate.now());

        ItemValue newValue = new ItemValue();
        newValue.setItem(item);
        newValue.setFromDate(dto.getFromDate() == null ? LocalDate.now() : dto.getFromDate());
        newValue.setPurchasePrice(dto.getPurchasePrice() == null ? currentValue.getPurchasePrice() : dto.getPurchasePrice());
        newValue.setSellingPrice(dto.getSellingPrice() == null ? currentValue.getSellingPrice() : dto.getSellingPrice());

        return repository.save(newValue);
    }
}
