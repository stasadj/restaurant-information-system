package com.restaurant.backend.controller;

import com.restaurant.backend.dto.CategoryDTO;
import com.restaurant.backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    private final CategoryService categoryService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return new ResponseEntity<>(categoryService.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<CategoryDTO> addOrEdit(@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(new CategoryDTO(categoryService.save(CategoryDTO.toDomain(categoryDTO))), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/drink")
    public ResponseEntity<List<CategoryDTO>> getDrinkCategories() {
        return new ResponseEntity<>(categoryService.getDrinkCategories().stream().map(CategoryDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }
}
