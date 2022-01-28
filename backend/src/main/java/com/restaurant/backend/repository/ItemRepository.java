package com.restaurant.backend.repository;

import java.util.List;

import com.restaurant.backend.domain.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByInMenuTrue();
    List<Item> findAllByCategory_Id(Long categoryId);
    @Query("select item from Item item where item.inMenu = true and item.deleted = false and item.category.id = ?1 and lower(item.name) like lower(concat('%', ?2, '%'))")
    List<Item> searchMenuItems(Long categoryId, String searchInput);
    List<Item> findAllByCategory_IdAndInMenuTrue(Long categoryId);
}
