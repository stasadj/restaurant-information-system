package com.restaurant.backend.repository;

import java.util.List;
import java.util.Optional;

import com.restaurant.backend.domain.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findAll();
    Optional<Item> findById(Long id);
    public List<Item> findByInMenuTrue();

}
