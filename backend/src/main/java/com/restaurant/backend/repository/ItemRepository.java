package com.restaurant.backend.repository;

import java.util.List;

import com.restaurant.backend.domain.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByInMenuTrue();

	List<Item> findAllByCategory_Id(Long categoryId);

	List<Item> findAllByCategory_IdAndInMenuTrue(Long categoryId);

	List<Item> findByInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameAsc(String searchInput);

	List<Item> findByInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameDesc(String searchInput);


	List<Item> findByCategory_IdAndInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameAsc(Long categoryId,
			String searchInput);

	List<Item> findByCategory_IdAndInMenuTrueAndDeletedFalseAndNameIgnoreCaseContainingOrderByNameDesc(Long categoryId,
			String searchInput);

}
