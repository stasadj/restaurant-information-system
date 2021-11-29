package com.restaurant.backend.controller;

import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.ItemValueDTO;
import com.restaurant.backend.dto.requests.ChangePriceDTO;
import com.restaurant.backend.service.ItemService;
import com.restaurant.backend.validation.interfaces.CreateInfo;
import com.restaurant.backend.validation.interfaces.EditInfo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    @ResponseBody
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> getById(@PathVariable Long id) {
        LOG.info("Client requested the get item by id method.");
        return new ResponseEntity<>(new ItemDTO(itemService.findOne(id)), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<ItemDTO>> getAll() {
        LOG.info("Client requested the get all items method.");
        return new ResponseEntity<>(itemService.getAll().stream().map(ItemDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/in-menu")
    public ResponseEntity<List<ItemDTO>> getAllMenuItems() {
        LOG.info("Client requested to get all menu items.");
        return new ResponseEntity<>(itemService.getAllMenuItems().stream().map(ItemDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/add-to-menu/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> addToMenu(@PathVariable Long id) {
        LOG.info("Client requested the add item to menu.");
        return new ResponseEntity<>(new ItemDTO(itemService.addToMenu(id)), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/remove-from-menu/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> removeFromMenu(@PathVariable Long id) {
        LOG.info("Client requested the remove item from menu.");
        return new ResponseEntity<>(new ItemDTO(itemService.removeFromMenu(id)), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOG.info("Client requested to delete item.");
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/create")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> create(@Validated(CreateInfo.class) @RequestBody ItemDTO itemDTO) {
        LOG.info("Client requested to create new item.");
        return new ResponseEntity<>(new ItemDTO(itemService.create(ItemDTO.toDomain(itemDTO))), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> editItem(@Validated(EditInfo.class) @RequestBody ItemDTO itemDTO) {
        LOG.info("Client requested to edit item.");
        return new ResponseEntity<>(new ItemDTO(itemService.editItem(ItemDTO.toDomain(itemDTO))), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/change-price")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemValueDTO> changeItemPrice(@Valid @RequestBody ChangePriceDTO changePriceDTO) {
        LOG.info("Client requested to change item price.");
        return new ResponseEntity<>(new ItemValueDTO(itemService.changeItemPrice(changePriceDTO)), HttpStatus.OK);
    }

}
