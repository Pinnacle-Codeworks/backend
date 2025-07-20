package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.base.exceptions.DomainException;

public class AgendasOnDifferentDateException extends DomainException {
  public AgendasOnDifferentDateException() {
    super("event-day-agendas-must-be-on-same-date");
  }
}