package com.restaurant.backend.support;

public interface ObjectMapper<TDomain, TDto> {
    public TDto convertToDto(TDomain domain);

    public TDomain convertToDomain(TDto dto);
}