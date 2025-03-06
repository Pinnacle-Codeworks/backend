package com.markguiang.backend.exceptions;

import jakarta.validation.ValidationException;

public class UniqueConstraintViolationException extends ValidationException {

    public UniqueConstraintViolationException(String uniqueField) {
        super(uniqueField + " should be unique.");
    }
}
