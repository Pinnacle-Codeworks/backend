package com.markguiang.backend.event.exceptions;

import com.markguiang.backend.base.exceptions.EntityDoesNotExistException;
import java.util.UUID;

public class EventDoesNotExistException extends EntityDoesNotExistException {
  public EventDoesNotExistException(UUID ID) {
    super("event", "id", ID.toString());
  }
}
