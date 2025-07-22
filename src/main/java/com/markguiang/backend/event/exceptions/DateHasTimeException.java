package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.base.exceptions.DomainException;

public class DateHasTimeException extends DomainException {
  public DateHasTimeException() {
    super("event-day-must-not-have-time");
  }
}