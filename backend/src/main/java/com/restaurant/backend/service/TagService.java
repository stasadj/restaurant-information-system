package com.restaurant.backend.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.exception.NotFoundException;
import com.restaurant.backend.repository.TagRepository;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TagService implements GenericService<Tag> {
    private final TagRepository tagRepository;
    private final EntityManager entityManager;


    @Override
    public List<Tag> findAll() {
        // Retrieves undeleted tags
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedItemFilter");
		filter.setParameter("isDeleted", false);
		List<Tag> tags = tagRepository.findAll();
		session.disableFilter("deletedItemFilter");
		return tags;
    }

    public List<Tag> getAllPlusDeleted() {
		// Retrieves all items, deleted included
		return tagRepository.findAll();
	}

    @Override
    public Tag findOne(Long id) throws NotFoundException {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No tag with id %d has been found", id)));
    }

    @Override
    public Tag save(Tag entity) {
        entity.setDeleted(false);
        Optional<Tag> maybeTag = tagRepository.findByName(entity.getName());
        return maybeTag.orElseGet(() -> tagRepository.save(entity));
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
