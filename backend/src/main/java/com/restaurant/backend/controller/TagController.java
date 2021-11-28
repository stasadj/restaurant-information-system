package com.restaurant.backend.controller;

import com.restaurant.backend.dto.TagDTO;
import com.restaurant.backend.service.TagService;
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
@RequestMapping(value = "/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private final TagService tagService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<TagDTO>> getAll() {
        return new ResponseEntity<>(tagService.findAll().stream().map(TagDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TagDTO> addOrEdit(@Valid @RequestBody TagDTO tagDTO) {
        return new ResponseEntity<>(new TagDTO(tagService.save(TagDTO.toDomain(tagDTO))), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
