package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.base.exceptions.DomainException;

public class DuplicateNameException extends DomainException {
  public DuplicateNameException(String name) {
    super("event-with-name-" + name + "-already-exists");
  }
}