package com.markguiang.backend.base.model;

import com.markguiang.backend.base.exceptions.MissingFieldException;
import java.util.Objects;
import java.util.UUID;

public abstract class Entity extends IdentifiableDomainObject {
  protected Entity(UUID ID) {
    super(ID);
  }

  public static <T> T requireNonNull(T o, String fieldName) {
    try {
      return Objects.requireNonNull(o);
    } catch (Exception e) {
      throw new MissingFieldException(fieldName);
    }
  }
}