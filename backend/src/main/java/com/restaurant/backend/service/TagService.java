package com.restaurant.backend.service;

import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService implements GenericService<Tag> {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findOne(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No tag with id %d has been found", id)));
    }

    @Override
    public Tag save(Tag entity) {
        return tagRepository.save(entity);
    }
}
