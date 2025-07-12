package com.markguiang.backend.base;

import com.markguiang.backend.exceptions.MissingFieldException;
import java.util.Objects;

public abstract class Entity<IDType> extends IdentifiableDomainObject<IDType> {

  public static <T> T requireNonNull(T o, String fieldName) {
    try {
      return Objects.requireNonNull(o);
    } catch (Exception e) {
      throw new MissingFieldException(fieldName);
    }
  }
}
