package com.restaurant.backend.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomConstraintViolationException extends RuntimeException {
    public CustomConstraintViolationException(String message) {
        super(message);
    }
}
