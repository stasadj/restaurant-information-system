package com.restaurant.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.backend.domain.StaffPaymentItem;

public interface StaffPaymentItemRepository extends JpaRepository<StaffPaymentItem, Long> {
	List<StaffPaymentItem> findAllByUser_IdOrderByDateTimeAsc(Long id);

}
