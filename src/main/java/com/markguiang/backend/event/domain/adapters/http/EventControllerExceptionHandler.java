package com.markguiang.backend.event.domain.adapters.http;

import com.markguiang.backend.event.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = { EventController.class })
public class EventControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
      AgendaNotFoundException.class,
      DayNotFoundException.class,
      EventDoesNotExistException.class
  })
  protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler({
      AgendasOnDifferentDateException.class,
      DateHasTimeException.class,
      DaysOnSameDateException.class,
      OverlappingAgendaTimeException.class
  })
  protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({ DuplicateNameException.class })
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
  }
}
