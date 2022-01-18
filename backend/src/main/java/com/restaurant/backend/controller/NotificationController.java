package com.restaurant.backend.controller;

import com.restaurant.backend.dto.responses.NotificationDTO;
import com.restaurant.backend.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{waiterId}")
    public ResponseEntity<List<NotificationDTO>> getAllForWaiter(@PathVariable Long waiterId) {
        return new ResponseEntity<>(
                notificationService.getAllForWaiter(waiterId)
                        .stream().map(NotificationDTO::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
