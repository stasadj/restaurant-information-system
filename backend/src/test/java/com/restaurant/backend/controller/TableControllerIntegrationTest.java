package com.restaurant.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
public class TableControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testSet_OK() throws Exception {
        String json =
                "{\"rooms\":[{\"id\": \"Room 1\", \"tables\": [ { \"id\": 1, \"rotateValue\": 0, \"size\": { \"w\": 150, \"h\": 50 }, \"radius\": 0, \"position\": { \"x\": 0, \"y\": 0 }}]}]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/table")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSet_positionMissing() throws Exception {
        String json =
                "{\"rooms\":[{\"id\": \"Room 1\", \"tables\": [ { \"id\": 1, \"rotateValue\": 0, \"size\": { \"w\": 150, \"h\": 50 }, \"radius\": 0}]}]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/table")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGet_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/table"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
