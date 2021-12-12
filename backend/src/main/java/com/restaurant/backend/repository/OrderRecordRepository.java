package com.restaurant.backend.repository;

import com.restaurant.backend.domain.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long> {

    @Query("SELECT r FROM OrderRecord r WHERE r.createdAt BETWEEN :fromDate AND :toDate " +
                "AND r.itemValue.id IN (SELECT id FROM ItemValue WHERE item.id = :itemId)")
    List<OrderRecord> getAllOrderRecordsBetweenDatesForItem(@Param("itemId") Long itemId,
                                                            @Param("fromDate") LocalDateTime fromDate,
                                                            @Param("toDate") LocalDateTime toDate);

    @Query("SELECT r FROM OrderRecord r WHERE r.createdAt BETWEEN :fromDate AND :toDate")
    List<OrderRecord> getAllOrderRecordsBetweenDates(@Param("fromDate") LocalDateTime fromDate,
                                                     @Param("toDate") LocalDateTime toDate);
}
