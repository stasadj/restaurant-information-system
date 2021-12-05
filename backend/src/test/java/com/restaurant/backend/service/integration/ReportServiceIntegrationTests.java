package com.restaurant.backend.service.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.transaction.Transactional;

import com.restaurant.backend.dto.reports.ReportQueryDTO;
import com.restaurant.backend.dto.reports.ReportResultsDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.service.ReportService;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static com.restaurant.backend.constants.ReportServiceTestConstants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:report_service_integration.sql")
@Transactional
public class ReportServiceIntegrationTests {
    @Autowired
    private ReportService reportService;

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
