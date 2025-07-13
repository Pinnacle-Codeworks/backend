package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.exceptions.DomainException;
import java.util.UUID;

public class EventDoesNotExistException extends DomainException {
  public EventDoesNotExistException(UUID ID) {
    super("event-with-ID-" + ID.toString() + "-does-not-exist");
  }
}
