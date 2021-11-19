package com.restaurant.backend.support;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericObjectMapper<TDomain, TDto> implements ObjectMapper<TDomain, TDto> {
    public List<TDto> convert(List<TDomain> collection) {
        return collection.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
