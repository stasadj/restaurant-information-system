package com.restaurant.backend.controller;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.dto.OrderItemIds;
import com.restaurant.backend.service.OrderItemService;
import com.restaurant.backend.support.OrderItemToOrderItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order-items", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderItemToOrderItemDTO toOrderItemDTO;

    @PreAuthorize("hasAnyRole('BARMAN', 'COOK')")
    @PutMapping("/accept")
    public ResponseEntity<List<OrderItemDTO>> acceptOrderItems(@AuthenticationPrincipal Staff staff,
                                                               @RequestBody OrderItemIds orderItemIds) {
        return new ResponseEntity<>(toOrderItemDTO.convert(orderItemService.acceptOrderItems(staff, orderItemIds.ids)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('BARMAN', 'COOK')")
    @PutMapping("/decline")
    public ResponseEntity<List<OrderItemDTO>> declineOrderItems(@AuthenticationPrincipal Staff staff,
                                                                @RequestBody OrderItemIds orderItemIds) {
        return new ResponseEntity<>(toOrderItemDTO.convert(orderItemService.declineOrderItems(staff, orderItemIds.ids)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('BARMAN', 'COOK')")
    @PutMapping("/mark-prepared")
    public ResponseEntity<List<OrderItemDTO>> markOrderItemsAsPrepared(@AuthenticationPrincipal Staff staff,
                                                                       @RequestBody OrderItemIds orderItemIds) {
        return new ResponseEntity<>(toOrderItemDTO.convert(orderItemService.prepareOrderItems(staff, orderItemIds.ids)), HttpStatus.OK);
    }
}
