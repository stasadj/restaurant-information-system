package com.restaurant.backend.service;

import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService implements GenericService<Tag> {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findOne(Long id) throws NotFoundException {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No tag with id %d has been found", id)));
    }

    @Override
    public Tag save(Tag entity) {
        Optional<Tag> maybeTag = tagRepository.findByName(entity.getName());
        return maybeTag.orElseGet(() -> tagRepository.save(entity));
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
