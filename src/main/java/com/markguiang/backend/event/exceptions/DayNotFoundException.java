package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.base.exceptions.DomainException;
import java.time.OffsetDateTime;

public class DayNotFoundException extends DomainException {
  public DayNotFoundException(OffsetDateTime date) {
    super("day-with-date-" + date.toString() + "-not-found");
  }
}