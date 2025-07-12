package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.exceptions.DomainException;

public class OverlappingAgendaTimeException extends DomainException {
  public OverlappingAgendaTimeException() {
    super("event-day-agendas-must-not-overlap");
  }
}
