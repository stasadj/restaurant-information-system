package com.restaurant.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.requests.OrderItemIds;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:order_item_service_integration.sql")
@Transactional
public final class OrderItemControllerIntegrationTest {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private Cookie cookie;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    private void login(Integer pin) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/pin-login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pin.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        cookie = result.getResponse().getCookie("accessToken");
    }

    @ParameterizedTest
    @MethodSource("forEmptyTest")
    public void test_empty_list(OrderItemIds ids, String url) throws Exception {
        login(1234);
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private static Collection<Object[]> forEmptyTest() {
        return Arrays.asList(new Object[][] {
                { new OrderItemIds(), "/api/order-items/accept" },
                { new OrderItemIds(new ArrayList<>()), "/api/order-items/accept" },
                { new OrderItemIds(null), "/api/order-items/accept" },
                { null, "/api/order-items/accept" },
                { new OrderItemIds(), "/api/order-items/decline" },
                { new OrderItemIds(new ArrayList<>()), "/api/order-items/decline" },
                { new OrderItemIds(null), "/api/order-items/decline" },
                { null, "/api/order-items/decline" },
                { new OrderItemIds(), "/api/order-items/mark-prepared" },
                { new OrderItemIds(new ArrayList<>()), "/api/order-items/mark-prepared" },
                { new OrderItemIds(null), "/api/order-items/mark-prepared" },
                { null, "/api/order-items/mark-prepared" },
        });
    }

    @Test
    public void acceptOrderItems_anOrderItemIsNotFound() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(9L, 1L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/accept")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order item #9 not found.\n"));

        OrderItem orderItem = orderItemService.findOne(1L);
        assertEquals(OrderStatus.IN_PROGRESS, orderItem.getOrderStatus());
        assertEquals(1234, orderItem.getCook().getPin());
    }

    @Test
    public void acceptOrderItems_anOrderItemIsNotPending() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(5L, 1L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/accept")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order item #5 is not pending.\n"));

        OrderItem orderItem = orderItemService.findOne(1L);
        assertEquals(OrderStatus.IN_PROGRESS, orderItem.getOrderStatus());
        assertEquals(1234, orderItem.getCook().getPin());
    }

    @Test
    public void acceptOrderItems_anOrderItemCannotBeAccepted() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(2L, 1L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/accept")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("DRINK order item #2 cannot be accepted by cook.\n"));

        OrderItem orderItem = orderItemService.findOne(1L);
        assertEquals(OrderStatus.IN_PROGRESS, orderItem.getOrderStatus());
        assertEquals(1234, orderItem.getCook().getPin());
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotFound() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(9L, 1L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/decline")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order item #9 not found.\n"));

        OrderItem orderItem = orderItemService.findOne(1L);
        assertEquals(OrderStatus.DECLINED, orderItem.getOrderStatus());
    }

    @Test
    public void declineOrderItems_anOrderItemIsNotPending() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(5L, 1L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/decline")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order item #5 is not pending.\n"));

        OrderItem orderItem = orderItemService.findOne(1L);
        assertEquals(OrderStatus.DECLINED, orderItem.getOrderStatus());
    }

    @Test
    public void declineOrderItems_anOrderItemCannotBeDeclined() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(2L, 1L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/decline")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("DRINK order item #2 cannot be declined by cook.\n"));

        OrderItem orderItem = orderItemService.findOne(1L);
        assertEquals(OrderStatus.DECLINED, orderItem.getOrderStatus());
    }

    @Test
    public void prepareOrderItems_anOrderItemIsNotFound() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(9L, 5L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/mark-prepared")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order item #9 not found.\n"));

        OrderItem orderItem = orderItemService.findOne(5L);
        assertEquals(OrderStatus.READY, orderItem.getOrderStatus());
    }

    @Test
    public void prepareOrderItems_anOrderItemIsNotInProgress() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(1L, 5L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/mark-prepared")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order item #1 is not in progress.\n"));

        OrderItem orderItem = orderItemService.findOne(5L);
        assertEquals(OrderStatus.READY, orderItem.getOrderStatus());
    }

    @Test
    public void prepareOrderItems_anOrderItemCannotBePrepared() throws Exception {
        login(1234);
        OrderItemIds ids = new OrderItemIds(List.of(6L, 5L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/order-items/mark-prepared")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("DRINK order item #6 cannot be prepared by cook.\n"));

        OrderItem orderItem = orderItemService.findOne(5L);
        assertEquals(OrderStatus.READY, orderItem.getOrderStatus());
    }

    @Test
    public void cancelOrderItems_anOrderItemIsNotFound() throws Exception {
        login(9000);
        OrderItemIds ids = new OrderItemIds(List.of(9L, 1L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/order-items/cancel")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order item #9 not found.\n"));

        assertThrows(NotFoundException.class, () -> orderItemService.findOne(1L));
    }

    @Test
    public void cancelOrderItems_anOrderItemCannotBeCancelled() throws Exception {
        login(9000);
        OrderItemIds ids = new OrderItemIds(List.of(5L, 1L));
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/order-items/cancel")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ids)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order item #5 cannot be cancelled.\n"));

        assertThrows(NotFoundException.class, () -> orderItemService.findOne(1L));
    }
}
