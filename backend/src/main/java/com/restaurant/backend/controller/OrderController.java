package com.restaurant.backend.controller;

import com.restaurant.backend.domain.OrderRecord;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.service.OrderService;
import com.restaurant.backend.support.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private final OrderMapper orderMapper;

	@PreAuthorize("hasRole('WAITER')")
	@GetMapping("/all")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		return new ResponseEntity<>(orderMapper.convertAll(orderService.findAll()), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('WAITER')")
	@GetMapping("/all/{id}")
	public ResponseEntity<List<OrderDTO>> getAllOrdersForWaiter(@PathVariable("id") Long id) {
		return new ResponseEntity<>(orderMapper.convertAll(orderService.findAllForWaiter(id)), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('WAITER')")
	@PostMapping("/create")
	@SendTo("/topic/orders")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
		return new ResponseEntity<>(orderMapper.convert(orderService.create(order)), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('WAITER')")
	@PutMapping("/edit")
	@SendTo("/topic/orders")
	public ResponseEntity<OrderDTO> editOrder(@RequestBody OrderDTO order) {
		return new ResponseEntity<>(orderMapper.convert(orderService.editOrder(order)), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('WAITER')")
	@PostMapping("/finalize/{id}")
	public ResponseEntity<List<OrderRecord>> finalizeOrder(@PathVariable("id") Long id) {
		return new ResponseEntity<>(orderService.finalizeOrder(id), HttpStatus.OK);
	}
}
