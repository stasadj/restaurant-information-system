package com.restaurant.backend.service.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.OrderRecord;
import com.restaurant.backend.service.OrderRecordService;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static com.restaurant.backend.constants.OrderRecordServiceTestConstants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:order_record_service_integration.sql")
@Transactional
public class OrderRecordServiceIntegrationTests {
    @Autowired
    private OrderRecordService orderRecordService;

    @ParameterizedTest
    @MethodSource("data")
    public void getAllOrderRecordsBetweenDatesForItem_parameterized_checkCount(Long itemId, LocalDate fromDate,
            LocalDate toDate, int count) {
        List<OrderRecord> records;
        if (itemId == null) {
            records = orderRecordService.getAllOrderRecordsBetweenDates(fromDate, toDate);
        } else {
            records = orderRecordService.getAllOrderRecordsBetweenDatesForItem(itemId, fromDate, toDate);
        }
        assertEquals(count, records.size());
    }

    /// Returns a collection of [Item ID, From Date, To Date, Count]
    private static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { null, FROM_DATE_ONE, TO_DATE_ONE, COUNT_ONE },
                { null, FROM_DATE_TWO, TO_DATE_TWO, COUNT_TWO },
                { null, FROM_DATE_THREE, TO_DATE_THREE, COUNT_THREE },
                { null, FROM_DATE_FOUR, TO_DATE_FOUR, COUNT_FOUR },
                { null, FROM_DATE_FIVE, TO_DATE_FIVE, COUNT_FIVE },
                { 1L, FROM_DATE_ONE, TO_DATE_ONE, COUNT_ONE_WITH_ID },
                { 1L, FROM_DATE_TWO, TO_DATE_TWO, COUNT_TWO_WITH_ID },
                { 1L, FROM_DATE_THREE, TO_DATE_THREE, COUNT_THREE_WITH_ID },
                { 1L, FROM_DATE_FOUR, TO_DATE_FOUR, COUNT_FOUR_WITH_ID },
                { 1L, FROM_DATE_FIVE, TO_DATE_FIVE, COUNT_FIVE_WITH_ID }
        });
    }
}
