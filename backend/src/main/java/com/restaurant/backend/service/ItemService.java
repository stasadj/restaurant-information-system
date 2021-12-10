package com.restaurant.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.requests.ChangePriceDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.exception.CustomConstraintViolationException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.support.ItemMapper;
import com.restaurant.backend.validation.interfaces.CreateInfo;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemValueService itemValueService;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final EntityManager entityManager;
    private final ItemMapper itemMapper;
    private Validator validator;

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
        // Retrieves all not deleted items in the menu
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedItemFilter");
        filter.setParameter("isDeleted", false);
        List<Item> items = itemRepository.findByInMenuTrue();
        session.disableFilter("deletedItemFilter");
        return items;
    }

    public List<Item> getAllByCategory(Long categoryId) {
        return itemRepository.findAllByCategory_Id(categoryId);
    }

    public Item findOne(Long id) throws NotFoundException {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No item with id %d has been found", id)));
    }

    public Item addToMenu(Long id) throws NotFoundException {
        Item item = findOne(id);
        item.setInMenu(true);
        return itemRepository.save(item);
    }

    public Item removeFromMenu(Long id) throws NotFoundException {
        Item item = findOne(id);
        item.setInMenu(false);
        return itemRepository.save(item);
    }

    public void delete(Long id) throws NotFoundException {
        Item item = findOne(id);
        itemRepository.delete(item);
    }

    public Item create(@Validated(CreateInfo.class) ItemDTO itemDTO) throws NotFoundException {
        Item item = itemMapper.convertToDomain(itemDTO);
        item.setId(null);
        item.setDeleted(false); // initially false

        item.setCategory(categoryService.findOne(item.getCategory().getId())); 

        List<Tag> tags = new ArrayList<>();
        item.getTags().forEach(tag -> tags.add(tagService.findOne(tag.getId())));

        item.setTags(tags);
        Item savedItem = itemRepository.save(item);

        ItemValue initialItemValue = item.getItemValues().get(0); // getting the only item value
        initialItemValue.setFromDate(LocalDateTime.now()); // current date as from date
        initialItemValue.setItem(savedItem);
        itemValueService.create(initialItemValue);

        return item;
    }

    public Item editItem(ItemDTO changedItemDTO) throws NotFoundException, ConstraintViolationException {

        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(changedItemDTO);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<ItemDTO> constraintViolation : violations) {
                System.out.println(constraintViolation.getMessage());
                sb.append(constraintViolation.getMessage());
            }
            throw new CustomConstraintViolationException(sb.toString());
        }

        Item changedItem = itemMapper.convertToDomain(changedItemDTO);
        Item item = findOne(changedItem.getId());
        item.setName(changedItem.getName());
        item.setDescription(changedItem.getDescription());
        item.setImageURL(changedItem.getImageURL());
        item.setItemType(changedItem.getItemType());
        item.setInMenu(changedItem.getInMenu()); // todo maybe remove this line because we have separate methods for
        item.setDeleted(changedItem.getDeleted()); // same goes for this one

        item.setCategory(categoryService.findOne(changedItem.getCategory().getId()));

        List<Tag> tags = new ArrayList<>();
        changedItem.getTags().forEach(tag -> tags.add(tagService.findOne(tag.getId())));
        item.setTags(tags);

        return itemRepository.save(item);
    }

    public ItemValue changeItemPrice(ChangePriceDTO dto) throws NotFoundException {
        Item item = findOne(dto.getItemId());
        ItemValue currentValue = item.getItemValueAt(LocalDateTime.now());

        ItemValue newValue = new ItemValue();
        newValue.setItem(item);
        newValue.setFromDate(dto.getFromDate() == null ? LocalDateTime.now() : dto.getFromDate());
        newValue.setPurchasePrice(
                dto.getPurchasePrice() == null ? currentValue.getPurchasePrice() : dto.getPurchasePrice());
        newValue.setSellingPrice(
                dto.getSellingPrice() == null ? currentValue.getSellingPrice() : dto.getSellingPrice());

        return itemValueService.create(newValue);
    }
}
