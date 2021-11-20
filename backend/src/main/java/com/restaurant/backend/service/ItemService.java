package com.restaurant.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.CategoryRepository;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.repository.ItemValueRepository;
import com.restaurant.backend.repository.TagRepository;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;
    private TagRepository tagRepository;
    private CategoryRepository categoryRepository;
    private ItemValueRepository itemValueRepository;
    private EntityManager entityManager;

    public List<Item> getAll() {
        // Retrieves undeleted items
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", false);
        List<Item> items = itemRepository.findAll();
        session.disableFilter("deletedItemFilter");
        return items;

    }

    public List<Item> getAllPlusDeleted() {
        // Retrieves all items, deleted included
        return itemRepository.findAll();

    }

    public List<Item> getAllMenuItems() {
        // Retrieves all undeleted items in the menu
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", false);
        List<Item> items = itemRepository.findByInMenuTrue();
        session.disableFilter("deletedItemFilter");
        return items;
    }

    public Item getById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No item with id %d has been found", id)));

    }

    public Item addToMenu(Long id) throws NotFoundException {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setInMenu(true);
            itemRepository.save(item);
            return item;
        }

        throw new NotFoundException("Attemped to add unexisting item to menu");

    }

    public Item removeFromMenu(Long id) throws NotFoundException {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setInMenu(false);
            return itemRepository.save(item);
        }

        throw new NotFoundException("Attemped to remove unexisting item from menu");

    }

    public void delete(Long id) throws NotFoundException {
        Item item = this.getById(id);
        itemRepository.delete(item);
        // Optional<Item> optionalItem = itemRepository.findById(id);
        // optionalItem.ifPresentOrElse(item -> itemRepository.delete(item),
        // () -> new NotFoundException("Attempted to delete unexisting item"));

    }

    public Item create(Item item) throws NotFoundException {
        item.setDeleted(false); // initially false

        item.setCategory(
                categoryRepository.findById(item.getCategory().getId()).orElseThrow(() -> new NotFoundException(
                        String.format("No category with id %d has been found", item.getCategory().getId())))); // TODO
                                                                                                               // place
                                                                                                               // this
                                                                                                               // throw
                                                                                                               // in
                                                                                                               // CategoryService
                                                                                                               // somehow?

        List<Tag> tags = new ArrayList<>();
        item.getTags().forEach(tag -> tags.add(tagRepository.findById(tag.getId()).orElseThrow(
                () -> new NotFoundException(String.format("No tag with id %d has been found", tag.getId()))))); // TODO
                                                                                                                // place
                                                                                                                // this
                                                                                                                // throw
                                                                                                                // in
                                                                                                                // TagService
                                                                                                                // somehow?
        item.setTags(tags);
        Item savedItem = itemRepository.save(item);

        ItemValue initialItemValue = item.getItemValues().get(0); // getting the only item value
        initialItemValue.setFromDate(LocalDate.now()); // current date as from date
        initialItemValue.setItem(savedItem);
        itemValueRepository.save(initialItemValue);

        return item;
    }

}
