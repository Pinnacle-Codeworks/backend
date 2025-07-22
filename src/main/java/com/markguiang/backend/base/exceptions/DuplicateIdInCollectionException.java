package com.markguiang.backend.base.exceptions;

public class DuplicateIdInCollectionException extends DomainException {
  public DuplicateIdInCollectionException() {
    super("duplicate-id-in-entity-collection");
  }
}
