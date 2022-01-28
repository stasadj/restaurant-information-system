package com.restaurant.backend.controller;

import java.util.List;

import javax.validation.Valid;

import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.ItemValueDTO;
import com.restaurant.backend.dto.requests.ChangePriceDTO;
import com.restaurant.backend.service.ItemService;
import com.restaurant.backend.support.ItemMapper;
import com.restaurant.backend.validation.interfaces.CreateInfo;
import com.restaurant.backend.validation.interfaces.EditInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/item", produces = MediaType.APPLICATION_JSON_VALUE) 
public class ItemController {
    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @ResponseBody
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> getById(@PathVariable Long id) {
        LOG.info("Client requested the get item by id method.");
        return new ResponseEntity<>(itemMapper.convert(itemService.findOne(id)), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/all") 
    //@PreAuthorize("hasRole('MANAGER')") //for testing frontend
    public ResponseEntity<List<ItemDTO>> getAll() {
        LOG.info("Client requested the get all items method.");
        return new ResponseEntity<>(itemMapper.convertAll(itemService.getAll()), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAnyRole('WAITER', 'BARMAN')")
    public ResponseEntity<List<ItemDTO>> getAllByCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(itemMapper.convertAll(itemService.getAllByCategory(categoryId)), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/in-menu")
    public ResponseEntity<List<ItemDTO>> getAllMenuItems() {
        LOG.info("Client requested to get all menu items.");
        return new ResponseEntity<>(itemMapper.convertAll(itemService.getAllMenuItems()), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/add-to-menu/{id}")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> addToMenu(@PathVariable Long id) {
        LOG.info("Client requested the add item to menu.");
        return new ResponseEntity<>(itemMapper.convert(itemService.addToMenu(id)), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/remove-from-menu/{id}")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> removeFromMenu(@PathVariable Long id) {
        LOG.info("Client requested the remove item from menu.");
        return new ResponseEntity<>(itemMapper.convert(itemService.removeFromMenu(id)), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOG.info("Client requested to delete item.");
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/create")
    // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> create(@Validated(CreateInfo.class) @RequestBody ItemDTO itemDTO) {
        LOG.info("Client requested to create new item.");
        return new ResponseEntity<>(itemMapper.convert(itemService.create(itemDTO)), HttpStatus.OK);
    }

    @RequestMapping(value = "/createWithFile", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> create(@RequestPart("item") @Valid @Validated(CreateInfo.class) ItemDTO itemDTO,
    @RequestPart(value="file", required=false) MultipartFile file) {
        LOG.info("Client requested to create new item.");
        return new ResponseEntity<>(itemMapper.convert(itemService.create(itemDTO, file)), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/edit")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemDTO> editItem(@Validated(EditInfo.class) @RequestBody ItemDTO itemDTO) {
        LOG.info("Client requested to edit item.");
        return new ResponseEntity<>(itemMapper.convert(itemService.editItem(itemDTO)), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/change-price")
    // @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ItemValueDTO> changeItemPrice(@Valid @RequestBody ChangePriceDTO changePriceDTO) {
        LOG.info("Client requested to change item price.");
        return new ResponseEntity<>(new ItemValueDTO(itemService.changeItemPrice(changePriceDTO)), HttpStatus.OK);
    }

}
