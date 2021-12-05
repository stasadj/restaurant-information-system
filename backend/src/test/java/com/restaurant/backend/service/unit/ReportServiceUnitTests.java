package com.restaurant.backend.service.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Category;
import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.OrderRecord;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.dto.reports.ReportQueryDTO;
import com.restaurant.backend.dto.reports.ReportResultsDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.service.ItemService;
import com.restaurant.backend.service.OrderRecordService;
import com.restaurant.backend.service.ReportService;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static com.restaurant.backend.constants.ReportServiceTestConstants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
public class ReportServiceUnitTests {
    @MockBean
    private OrderRecordService orderRecordService;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ReportService reportService;

    @BeforeEach
    private void init() {
        ItemValue itemValue = new ItemValue();
        itemValue.setFromDate(DAILY_START_DATE.atStartOfDay());
        itemValue.setPurchasePrice(BigDecimal.valueOf(100));
        itemValue.setSellingPrice(BigDecimal.valueOf(1000));
        List<ItemValue> itemValues = List.of(itemValue);
        Category category = new Category(1L, "Food");
        List<Item> items = List.of(
            new Item(1L, "Some name", category, "Some description", "", null, true, itemValues, ItemType.FOOD, false),
            new Item(1L, "Some name", category, "Some description", "", null, true, itemValues, ItemType.FOOD, false),
            new Item(1L, "Some name", category, "Some description", "", null, true, itemValues, ItemType.FOOD, false)
        );
        when(itemService.findOne(anyLong())).thenReturn(items.get(0));
        when(itemService.getAll()).thenReturn(items);

        List<OrderRecord> orderRecords = List.of(
            new OrderRecord(1L, ORDER_DATE_ONE.atStartOfDay(), 1, itemValue),
            new OrderRecord(2L, ORDER_DATE_TWO.atStartOfDay(), 2, itemValue),
            new OrderRecord(3L, ORDER_DATE_THREE.atStartOfDay(), 4, itemValue),
            new OrderRecord(4L, ORDER_DATE_FOUR.atStartOfDay(), 6, itemValue)
        );
        when(orderRecordService.getAllOrderRecordsBetweenDatesForItem(anyLong(), any(), any())).thenReturn(orderRecords);
        when(orderRecordService.getAllOrderRecordsBetweenDates(any(), any())).thenReturn(List.of(orderRecords.get(0)));
    }

    @ParameterizedTest
    @MethodSource("dtos")
    public void getResults_parameterizedQueries(ReportQueryDTO dto, int datapointCount, int itemCount) {
        ReportResultsDTO result = reportService.getResults(dto);
        assertEquals(datapointCount, result.getDatapoints().size());
        assertEquals(itemCount, result.getIndividualItems().size());
    }

    @Test
    public void getResults_invalidReportType() {
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            reportService.getResults(INVALID_REPORT_TYPE_QUERY);
        }, "BadRequestException was expected");

        assertEquals("Bad query.", thrown.getMessage());
    }

    @Test
    public void getResults_invalidGranularity() {
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            reportService.getResults(INVALID_GRANULARITY_QUERY);
        }, "BadRequestException was expected");

        assertEquals("Bad query.", thrown.getMessage());
    }

    @Test
    public void getResults_itemHistogramQuery_missingItemId() {
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            reportService.getResults(MISSING_ITEM_ID_QUERY);
        }, "BadRequestException was expected");

        assertEquals("Bad query.", thrown.getMessage());
    }

    /// Returns a collection of [Query, DataPoint Count, Item Count]
    private static Collection<Object> dtos() {
        return Arrays.asList(new Object[][] {
                { DAILY_PROFIT_QUERY, 365, 3 },
                { DAILY_PROFIT_QUERY_FOR_ITEM, 365, 1 },
                { DAILY_PRICE_HISTORY_QUERY, 365, 1 },
                { WEEKLY_PROFIT_QUERY, 5, 3 },
                { WEEKLY_PROFIT_QUERY_FOR_ITEM, 5, 1 },
                { WEEKLY_PRICE_HISTORY_QUERY, 5, 1 },
                { MONTHLY_PROFIT_QUERY, 12, 3 },
                { MONTHLY_PROFIT_QUERY_FOR_ITEM, 12, 1 },
                { MONTHLY_PRICE_HISTORY_QUERY, 12, 1 },
                { QUARTERLY_PROFIT_QUERY, 4, 3 },
                { QUARTERLY_PROFIT_QUERY_FOR_ITEM, 4, 1 },
                { QUARTERLY_PRICE_HISTORY_QUERY, 4, 1 },
                { YEARLY_PROFIT_QUERY, 2, 3 },
                { YEARLY_PROFIT_QUERY_FOR_ITEM, 2, 1 },
                { YEARLY_PRICE_HISTORY_QUERY, 2, 1 },
        });
    }
}
