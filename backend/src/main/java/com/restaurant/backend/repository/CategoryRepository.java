package com.restaurant.backend.repository;

import com.restaurant.backend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query(value = "SELECT COUNT(id) FROM item WHERE category_id = :id", nativeQuery = true)
    int timesUsed(Long id);
}
