package com.restaurant.backend.controller;

import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.service.OrderService;
import com.restaurant.backend.support.OrderToOrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderToOrderDTO toOrderDTO;

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return new ResponseEntity<>(toOrderDTO.convert(orderService.findAll()), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(toOrderDTO.convert(orderService.create(order)), HttpStatus.OK);
    }
}
