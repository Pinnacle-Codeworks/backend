package com.markguiang.backend.base;

import com.fasterxml.uuid.Generators;
import java.util.Objects;
import java.util.UUID;

public abstract class IdentifiableDomainObject implements DomainObject {
  private UUID ID;

  protected IdentifiableDomainObject() {
    this.ID = Generators.timeBasedEpochGenerator().generate();
  }

  public UUID getID() {
    return this.ID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    IdentifiableDomainObject entity = (IdentifiableDomainObject) o;
    return Objects.equals(ID, entity.getID());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(ID);
  }
}
