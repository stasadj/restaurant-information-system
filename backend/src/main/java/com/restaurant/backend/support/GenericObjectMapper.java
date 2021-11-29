package com.restaurant.backend.support;

import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericObjectMapper<TDomain, TDto> implements Converter<TDomain, TDto> {
    public List<TDto> convertAll(List<TDomain> collection) {
        return collection.stream().map(this::convert).collect(Collectors.toList());
    }
}
