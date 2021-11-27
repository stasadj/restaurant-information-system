package com.restaurant.backend.service;

import com.restaurant.backend.domain.OrderRecord;
import com.restaurant.backend.repository.OrderRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderRecordService {
    private final OrderRecordRepository orderRecordRepository;

    public List<OrderRecord> saveAll(List<OrderRecord> orderRecords) {
        return orderRecordRepository.saveAll(orderRecords);
    }

    public List<OrderRecord> getAllOrderRecordsBetweenDatesForItem(Long itemId, LocalDate fromDate, LocalDate toDate) {
        return orderRecordRepository.getAllOrderRecordsBetweenDatesForItem(itemId, fromDate, toDate);
    }

    public List<OrderRecord> getAllOrderRecordsBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return orderRecordRepository.getAllOrderRecordsBetweenDates(fromDate, toDate);
    }
}
