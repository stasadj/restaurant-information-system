package com.restaurant.backend.controller;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restaurant.backend.dto.reports.ReportQueryDTO;
import com.restaurant.backend.dto.reports.ReportResultsDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.restaurant.backend.constants.ReportControllerTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:report_controller_integration.sql")
@Transactional
public class ReportControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @WithMockUser(authorities = {"ROLE_MANAGER"})
    @ParameterizedTest
    @MethodSource("dtos")
    public void getResults_parameterizedQueries(ReportQueryDTO dto, Integer datapointCount, Integer itemCount)
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/report/query")
                .header("Content-type", "application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();

        ReportResultsDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ReportResultsDTO.class);
        assertEquals(datapointCount, result.getDatapoints().size());
        assertEquals(itemCount, result.getIndividualItems().size());
    }

    /// Returns a collection of [Query, DataPoint Count, Item Count]
    private static Collection<Object[]> dtos() {
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
