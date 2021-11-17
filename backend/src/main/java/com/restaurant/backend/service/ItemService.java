package com.restaurant.backend.service;

import java.util.List;
import java.util.Optional;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.ItemRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {
    
    private ItemRepository itemRepository;

    public List<Item> getAll(){
        return itemRepository.findAll();
    }

    public List<Item> getAllMenuItems() {
        return itemRepository.findByInMenuTrue();
    }

    public Optional<Item> getById(long id){
        return itemRepository.findById(id);
    }

    public Item addToMenu(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent())
        {
            Item item = optionalItem.get();
            item.setInMenu(true);
            itemRepository.save(item);
            return item;
        };
        throw new NotFoundException("Attemped to add unexisting item to menu");

    }

    public Item removeFromMenu(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent())
        {
            Item item = optionalItem.get();
            item.setInMenu(false);
            itemRepository.save(item);
            return item;
        };
        throw new NotFoundException("Attemped to remove unexisting item from menu");

    }


}
