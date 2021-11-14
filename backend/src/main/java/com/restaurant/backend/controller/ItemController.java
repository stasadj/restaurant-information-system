package com.restaurant.backend.controller;


import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.restaurant.backend.domain.Item;
import com.restaurant.backend.service.ItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/api/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @ResponseBody
    @GetMapping("/all")
    //@PreAuthorize("hasRole('MANAGER')")
    public String getAll() {
        LOG.info("Client requested the get all items method.");
        List<Item> items = itemService.getAll();
        StringBuilder sb = new StringBuilder();
        items.forEach(item -> sb.append(item.getName() + "\n"));
        return sb.toString();
    }

    @ResponseBody
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('MANAGER')")
    public String getById(@PathVariable Long id) {
        LOG.info("Client requested the get item by id method.");
        Optional<Item> item = itemService.getById(id);
        StringBuilder sb = new StringBuilder();
        item.ifPresent(i -> sb.append(i.getName()));
        return sb.toString();
    }


}
