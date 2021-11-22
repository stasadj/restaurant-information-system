package com.restaurant.backend.repository;

import com.restaurant.backend.domain.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long> {

    @Query(
        value = "SELECT r FROM order_records r WHERE r.created_at BETWEEN ?2 AND ?3 " +
                "AND r.item_value_id IN (SELECT id FROM item_values WHERE item_id = ?1);",
        nativeQuery = true)
    List<OrderRecord> getAllOrderRecordsBetweenDatesForItem(Long itemId, LocalDate fromDate, LocalDate toDate);

    @Query(value = "SELECT r FROM order_records r WHERE r.created_at BETWEEN ?1 AND ?2 ", nativeQuery = true)
    List<OrderRecord> getAllOrderRecordsBetweenDates(LocalDate fromDate, LocalDate toDate);
}
