package com.restaurant.backend.repository;

import com.restaurant.backend.domain.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
