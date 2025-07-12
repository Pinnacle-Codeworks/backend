package com.markguiang.backend.base;

import java.util.Objects;

public abstract class IdentifiableDomainObject<IDType> implements DomainObject {
  private IDType ID;

  public IDType getID() {
    return this.ID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Entity<?> entity = (Entity<?>) o;
    return Objects.equals(ID, entity.getID());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(ID);
  }
}
