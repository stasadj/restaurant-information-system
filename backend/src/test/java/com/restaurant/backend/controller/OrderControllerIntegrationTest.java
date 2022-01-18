package com.restaurant.backend.controller;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.dto.OrderItemDTO;

import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:order_service_integration.sql")
@Transactional
public class OrderControllerIntegrationTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@WithMockUser(authorities = { "ROLE_WAITER" })
	@Test
	public void getAllOrders() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/order/all")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
	}

	@WithMockUser(authorities = { "ROLE_WAITER" })
	@Test
	public void getAllOrdersForWaiter_waiterDoesNotExist() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/order/all/" + "30"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
	}

	@WithMockUser(authorities = { "ROLE_WAITER" })
	@Test
	public void getAllOrdersForWaiter() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/order/all/" + "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
	}

	@WithMockUser(authorities = { "ROLE_WAITER" })
	@Test
	public void createOrder_tableHasAnOrder() throws Exception {
		OrderItemDTO itemDTO = new OrderItemDTO(1L, 2, null, OrderStatus.PENDING, 1L, null, null, null);
		OrderDTO orderDTO = new OrderDTO(null, null, "note", 1, Collections.singletonList(itemDTO), 1L);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@WithMockUser(authorities = { "ROLE_WAITER" })
	@Test
	public void createOrder() throws Exception {
		OrderItemDTO itemDTO = new OrderItemDTO(null, 2, null, OrderStatus.PENDING, 1L, null, null, null);
		OrderDTO orderDTO = new OrderDTO(null, null, "note", 4, Collections.singletonList(itemDTO), 1L);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderDTO))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.tableId").value(4))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].orderStatus").value("PENDING"));
	}

	@WithMockUser(authorities = { "ROLE_WAITER" })
	@Test
	public void editOrderItems() throws Exception {
		OrderItemDTO itemDTO1 = new OrderItemDTO(4L, 2, 2L, OrderStatus.PENDING, 4L, null, null, null);
		OrderItemDTO itemDTO2 = new OrderItemDTO(null, 1, null, OrderStatus.PENDING, 1L, null, null, null);
		OrderDTO orderDTO = new OrderDTO(2L, null, "note edited", 2, Arrays.asList(itemDTO1, itemDTO2), 1L);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/order/edit").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderDTO))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.tableId").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.waiterId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.note").value("note edited"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].amount").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[1].orderStatus").value("PENDING"));
	}

	@WithMockUser(authorities = { "ROLE_WAITER" })
	@Test
	public void finalizeOrder_orderItemNotReady() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/order/finalize/" + "1"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@WithMockUser(authorities = { "ROLE_WAITER" })
	@Test
	public void finalizeOrder() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/order/finalize/" + "3"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
	}

}
