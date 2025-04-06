package com.markguiang.backend.exceptions;

import jakarta.validation.ValidationException;

public class FieldMismatchException extends ValidationException {

    public FieldMismatchException(String field) {
        super(field + " does not match DB value");
    }
}
