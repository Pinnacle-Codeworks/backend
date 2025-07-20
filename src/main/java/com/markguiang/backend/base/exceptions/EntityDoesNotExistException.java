package com.markguiang.backend.base.exceptions;

import java.util.UUID;

public class EntityDoesNotExistException extends DomainException {
  public EntityDoesNotExistException(String entityName, UUID ID) {
    super(entityName + "-with-ID-" + ID.toString() + "-does-not-exist");
  }
}
