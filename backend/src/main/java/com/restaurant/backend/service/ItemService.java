package com.restaurant.backend.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.ItemRepository;

import org.hibernate.Session;
import org.hibernate.Filter;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;
    private EntityManager entityManager;

    public List<Item> getAll() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", false);
        List<Item> items = itemRepository.findAll();
        session.disableFilter("deletedItemFilter");
        return items;

    }

    public List<Item> getAllPlusDeleted() {
        return itemRepository.findAll();

    }

    public List<Item> getAllMenuItems() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", false);
        List<Item> items = itemRepository.findByInMenuTrue();
        session.disableFilter("deletedItemFilter");
        return items;
    }

    public Optional<Item> getById(long id) {
        return itemRepository.findById(id);
    }

    public Item addToMenu(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setInMenu(true);
            itemRepository.save(item);
            return item;
        }

        throw new NotFoundException("Attemped to add unexisting item to menu");

    }

    public Item removeFromMenu(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setInMenu(false);
            return itemRepository.save(item);
        }

        throw new NotFoundException("Attemped to remove unexisting item from menu");

    }

    public void delete(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        optionalItem.ifPresentOrElse(item -> itemRepository.delete(item),
                () -> new NotFoundException("Attempted to delete unexisting item"));

    }

}
