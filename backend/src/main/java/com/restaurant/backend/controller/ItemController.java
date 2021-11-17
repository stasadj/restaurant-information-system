package com.restaurant.backend.controller;


import java.util.List;
import java.util.Optional;


import com.restaurant.backend.domain.Item;
import com.restaurant.backend.service.ItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);

    private ItemService itemService;

    @ResponseBody
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Optional<Item>> getById(@PathVariable Long id) {
        LOG.info("Client requested the get item by id method.");
        return new ResponseEntity<>(itemService.getById(id), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/all")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Item>> getAll() {
        LOG.info("Client requested the get all items method.");
        return new ResponseEntity<>(itemService.getAll(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/in-menu")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Item>> getAllMenuItems() {
        LOG.info("Client requested to get all menu items.");
        return new ResponseEntity<>(itemService.getAllMenuItems(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/add-to-menu")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Item> addToMenu(@RequestParam Long id) {
        LOG.info("Client requested the add item to menu.");
        return new ResponseEntity<>(itemService.addToMenu(id), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/remove-from-menu")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Item> removeFromMenu(@RequestParam Long id) {
        LOG.info("Client requested the remove item from menu.");
        return new ResponseEntity<>(itemService.removeFromMenu(id), HttpStatus.OK);
    }


}
