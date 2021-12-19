package com.restaurant.backend.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.restaurant.backend.exception.CustomConstraintViolationException;

import java.util.Set;

public class DTOValidator {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T, G extends Default> void validate(T object, Class<G> group) throws CustomConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(object, group);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            violations.forEach(constraintViolation -> sb.append(constraintViolation.getMessage()));
            throw new CustomConstraintViolationException(sb.toString());
        }
    }

    public static <T> void validate(T object) throws CustomConstraintViolationException {
        Set<ConstraintViolation<T>> violations = validator.validate(object, Default.class);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            violations.forEach(constraintViolation -> sb.append(constraintViolation.getMessage()));
            throw new CustomConstraintViolationException(sb.toString());
        }
    }
}
