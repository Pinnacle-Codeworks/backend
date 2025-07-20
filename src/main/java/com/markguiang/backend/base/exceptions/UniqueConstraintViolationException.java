package com.markguiang.backend.base.exceptions;

import jakarta.validation.ValidationException;

public class UniqueConstraintViolationException extends ValidationException {

    public UniqueConstraintViolationException(String uniqueField) {
        super(uniqueField + " is already in use.");
    }
}