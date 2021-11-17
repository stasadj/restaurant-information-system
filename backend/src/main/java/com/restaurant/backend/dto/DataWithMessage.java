package com.restaurant.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataWithMessage<T> {
    private T data;
    private String message;
}
