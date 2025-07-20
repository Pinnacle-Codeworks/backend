package com.markguiang.backend.base.exceptions;

import jakarta.validation.ValidationException;

public class FieldMismatchException extends ValidationException {

    public FieldMismatchException(String field) {
        super(field + " does not match DB value");
    }
}