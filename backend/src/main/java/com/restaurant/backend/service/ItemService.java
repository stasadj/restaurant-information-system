package com.restaurant.backend.service;

import java.util.List;
import java.util.Optional;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAll(){
        return itemRepository.findAll();
    }

    public Optional<Item> getById(long id){
        return itemRepository.findById(id);
    }
}
