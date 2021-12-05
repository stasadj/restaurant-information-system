package com.restaurant.backend.repository;

import com.restaurant.backend.domain.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long> {

    @Query(
        value = "SELECT * FROM order_record WHERE created_at BETWEEN :fromDate AND :toDate " +
                "AND item_value_id IN (SELECT id FROM item_values WHERE item_id = :itemId);",
        nativeQuery = true)
    List<OrderRecord> getAllOrderRecordsBetweenDatesForItem(@Param("itemId") Long itemId,
                                                            @Param("fromDate") LocalDate fromDate,
                                                            @Param("toDate") LocalDate toDate);

    @Query(value = "SELECT * FROM order_record WHERE created_at BETWEEN :fromDate AND :toDate ;", nativeQuery = true)
    List<OrderRecord> getAllOrderRecordsBetweenDates(@Param("fromDate") LocalDate fromDate,
                                                     @Param("toDate") LocalDate toDate);
}
