package com.markguiang.backend.infrastructure.http;

import com.markguiang.backend.base.exceptions.FieldMismatchException;
import com.markguiang.backend.base.exceptions.UniqueConstraintViolationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Object> handleGeneralConstraintViolation (RuntimeException ex, WebRequest request) {
        String message = "Violated entity constraints";
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { UniqueConstraintViolationException.class })
    protected ResponseEntity<Object> handleUniqueConstraintViolation (RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { AuthorizationDeniedException.class })
    protected ResponseEntity<Object> handleAuthorizationDeniedException (RuntimeException ex, WebRequest request) {
        String message = "Not allowed";
        return handleExceptionInternal(ex, message,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = { AuthenticationCredentialsNotFoundException.class })
    protected ResponseEntity<Object> handleAuthenticationException (RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    protected ResponseEntity<Object> handleNoSuchElementException (RuntimeException ex, WebRequest request) {
        String message = "The resource does not exist";
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { FieldMismatchException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleIDMismatchException (RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}