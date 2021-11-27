package com.restaurant.backend.service;

import com.restaurant.backend.domain.Category;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService implements GenericService<Category> {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findOne(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No category with id %d has been found", id)));
    }

    @Override
    public Category save(Category entity) {
        Optional<Category> maybeCategory = categoryRepository.findByName(entity.getName());
        return maybeCategory.orElseGet(() -> categoryRepository.save(entity));
    }

    public void delete(Long id) {
        Category category = findOne(id);
        if (categoryRepository.timesUsed(category.getId()) == 0)
            categoryRepository.deleteById(id);
        // else cannot be deleted
    }
}
