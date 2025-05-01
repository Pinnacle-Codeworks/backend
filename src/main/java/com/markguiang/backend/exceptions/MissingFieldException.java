package com.markguiang.backend.exceptions;

import jakarta.validation.ValidationException;

public class MissingFieldException extends ValidationException {
    public MissingFieldException(String message) {
        super(message);
    }
}
