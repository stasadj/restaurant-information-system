package com.restaurant.backend.repository;

import com.restaurant.backend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT COUNT(id) FROM Item i WHERE i.category.id = :id")
    int timesUsed(Long id);

    @Query("SELECT DISTINCT i.category FROM Item i WHERE i.itemType = 1")
    List<Category> getDrinkCategories();
}
