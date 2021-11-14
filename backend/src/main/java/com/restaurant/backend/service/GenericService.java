package com.restaurant.backend.service;

import java.util.List;

public interface GenericService<T> {

    List<T> findAll();

    T findOne(Long id);

    T save(T entity);
}
