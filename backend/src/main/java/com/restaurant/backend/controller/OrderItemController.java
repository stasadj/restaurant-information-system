package com.restaurant.backend.controller;

import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.dto.requests.OrderItemIds;
import com.restaurant.backend.dto.responses.DataWithMessage;
import com.restaurant.backend.service.OrderItemService;
import com.restaurant.backend.support.OrderItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/order-items", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;

    private final SimpMessagingTemplate messagingTemplate;

    @PreAuthorize("hasAnyRole('BARMAN', 'COOK')")
    @PutMapping("/accept")
    public ResponseEntity<DataWithMessage<List<OrderItemDTO>>> acceptOrderItems(@AuthenticationPrincipal Staff staff,
                                                                               @Valid @RequestBody OrderItemIds orderItemIds) {
        DataWithMessage<List<OrderItem>> acceptedItems = orderItemService.acceptOrderItems(staff, orderItemIds.getIds());
        var data = orderItemMapper.convertAll(acceptedItems.getData());
        if (data != null)
            messagingTemplate.convertAndSend("/topic/order-items", data);
        return new ResponseEntity<>(new DataWithMessage<>(data, acceptedItems.getMessage()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('BARMAN', 'COOK')")
    @PutMapping("/decline")
    public ResponseEntity<DataWithMessage<List<OrderItemDTO>>> declineOrderItems(@AuthenticationPrincipal Staff staff,
                                                                @Valid @RequestBody OrderItemIds orderItemIds) {
        DataWithMessage<List<OrderItem>> declinedItems = orderItemService.declineOrderItems(staff, orderItemIds.getIds());
        var data = orderItemMapper.convertAll(declinedItems.getData());
        if (data != null)
            messagingTemplate.convertAndSend("/topic/order-items", data);
        return new ResponseEntity<>(new DataWithMessage<>(data, declinedItems.getMessage()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('BARMAN', 'COOK')")
    @PutMapping("/mark-prepared")
    public ResponseEntity<DataWithMessage<List<OrderItemDTO>>> markOrderItemsAsPrepared(@AuthenticationPrincipal Staff staff,
                                                                       @Valid @RequestBody OrderItemIds orderItemIds) {
        DataWithMessage<List<OrderItem>> preparedItems = orderItemService.prepareOrderItems(staff, orderItemIds.getIds());
        var data = orderItemMapper.convertAll(preparedItems.getData());
        if (data != null)
            messagingTemplate.convertAndSend("/topic/order-items", data);
        return new ResponseEntity<>(new DataWithMessage<>(data, preparedItems.getMessage()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('WAITER')")
    @PutMapping("/cancel")
    public ResponseEntity<DataWithMessage<List<Long>>> cancelOrderItems(@Valid @RequestBody OrderItemIds orderItemIds) {
        var data = orderItemService.cancelOrderItems(orderItemIds.getIds());
        if (data.getData() != null)
            messagingTemplate.convertAndSend("/topic/cancelled-items", data.getData());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
