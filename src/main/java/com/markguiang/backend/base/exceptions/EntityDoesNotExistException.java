package com.markguiang.backend.base.exceptions;

public class EntityDoesNotExistException extends DomainException {
  public EntityDoesNotExistException(String entityName, String fieldName, String value) {
    super(entityName + "-with-" + fieldName + "-" + value + "-does-not-exist");
  }
}
