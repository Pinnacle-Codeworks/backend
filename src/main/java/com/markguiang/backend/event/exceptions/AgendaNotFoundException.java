package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.base.exceptions.DomainException;
import java.time.OffsetDateTime;

public class AgendaNotFoundException extends DomainException {
  public AgendaNotFoundException(OffsetDateTime date) {
    super("agenda-with-start-date-" + date.toString() + "-not-found");
  }
}