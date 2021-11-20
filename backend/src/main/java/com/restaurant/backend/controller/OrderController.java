package com.restaurant.backend.controller;

import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.service.OrderService;
import com.restaurant.backend.support.OrderToOrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderToOrderDTO toOrderDTO;

    @PreAuthorize("hasRole('WAITER')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return new ResponseEntity<>(toOrderDTO.convert(orderService.findAll()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('WAITER')")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersForWaiter(@PathVariable("id") Long id) {
        return new ResponseEntity<>(toOrderDTO.convert(orderService.findAllForWaiter(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('WAITER')")
    @PostMapping("/create")
    public ResponseEntity<List<OrderDTO>> createOrder(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(toOrderDTO.convert(orderService.create(order)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('WAITER')")
    @PutMapping("/edit")
    public ResponseEntity<List<OrderDTO>> editOrderItems(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(toOrderDTO.convert(orderService.editOrderItems(order)), HttpStatus.OK);
    }

}
