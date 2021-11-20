
package com.restaurant.backend.repository;

import com.restaurant.backend.domain.ItemValue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemValueRepository extends JpaRepository<ItemValue, Long> {
}
