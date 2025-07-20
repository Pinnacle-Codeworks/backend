package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.base.exceptions.DomainException;

public class DaysOnSameDateException extends DomainException {
  public DaysOnSameDateException() {
    super("event-days-must-be-on-different-dates");
  }
}