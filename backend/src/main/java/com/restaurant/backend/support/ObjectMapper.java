package com.restaurant.backend.support;

public interface ObjectMapper<TDomain, TDto> {
    TDto convertToDto(TDomain domain);

    TDomain convertToDomain(TDto dto);
}