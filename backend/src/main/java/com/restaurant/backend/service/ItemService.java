package com.restaurant.backend.service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.requests.ChangePriceDTO;
import com.restaurant.backend.exception.CustomConstraintViolationException;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.service.storage.FileSystemStorageService;
import com.restaurant.backend.support.ItemCurrentValueComparator;
import com.restaurant.backend.support.ItemMapper;
import com.restaurant.backend.validation.DTOValidator;
import com.restaurant.backend.validation.interfaces.CreateInfo;
import com.restaurant.backend.validation.interfaces.EditInfo;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

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
	private final FileSystemStorageService storageService;

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
		return itemRepository.findAllByCategory_IdAndInMenuTrue(categoryId);
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

		DTOValidator.validate(itemDTO, CreateInfo.class);

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

	public Item create(@Validated(CreateInfo.class) ItemDTO itemDTO, MultipartFile file) {
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

		String imageFileName = null;
		if (file != null) {
			String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
			imageFileName = "image" + savedItem.getId() + "." + fileExtension; // setting unique image file name based
																				// on id from database
			storageService.store(file, imageFileName); // saving image to file system
		}

		savedItem.setImageURL(imageFileName);
		savedItem = itemRepository.save(savedItem);
		return item;

	}

	public Item editItem(@Validated(EditInfo.class) ItemDTO changedItemDTO)
			throws NotFoundException, CustomConstraintViolationException {

		DTOValidator.validate(changedItemDTO, EditInfo.class);

		Item item = findOne(changedItemDTO.getId());
		item.setName(changedItemDTO.getName());
		item.setDescription(changedItemDTO.getDescription());
		item.setImageURL(changedItemDTO.getImageFileName());
		item.setItemType(changedItemDTO.getItemType());
		item.setInMenu(changedItemDTO.getInMenu()); // todo maybe remove this line because we have separate methods for
													// adding to menu
		item.setDeleted(changedItemDTO.getDeleted()); // same goes for this one

		item.setCategory(categoryService.findOne(changedItemDTO.getCategory().getId()));

		List<Tag> tags = new ArrayList<>();
		changedItemDTO.getTags().forEach(tag -> tags.add(tagService.findOne(tag.getId())));
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

	public List<Item> searchMenuItems(Long categoryId, String searchInput, Integer sortCriteria) {
		List<Item> results;
		if (categoryId == 0) {
			switch (sortCriteria) {
			case 2:
				return itemRepository
						.findByInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameDesc(searchInput);
			case 3:
				results = itemRepository
						.findByInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameAsc(searchInput);
				Collections.sort(results, new ItemCurrentValueComparator(1));
				return results;
			case 4:
				results = itemRepository
						.findByInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameAsc(searchInput);
				Collections.sort(results, new ItemCurrentValueComparator(-1));
				return results;
			default:
				return itemRepository
						.findByInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameAsc(searchInput);
			}
		}
		switch (sortCriteria) {
		case 2:
			return itemRepository
					.findByCategory_IdAndInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameDesc(categoryId,
							searchInput);
		case 3:
			results = itemRepository
					.findByCategory_IdAndInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameAsc(categoryId,
							searchInput);
			Collections.sort(results, new ItemCurrentValueComparator(1));
			return results;
		case 4:
			results = itemRepository
					.findByCategory_IdAndInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameAsc(categoryId,
							searchInput);
			Collections.sort(results, new ItemCurrentValueComparator(-1));
			return results;
		default:
			return itemRepository
					.findByCategory_IdAndInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameAsc(categoryId,
							searchInput);
		}
	}
}
