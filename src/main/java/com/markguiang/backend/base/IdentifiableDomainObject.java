package com.markguiang.backend.base;

import com.fasterxml.uuid.Generators;
import java.util.Objects;
import java.util.UUID;

public abstract class IdentifiableDomainObject implements DomainObject {
  private final UUID id;

  protected IdentifiableDomainObject(UUID id) {
    if (id == null) {
      this.id = Generators.timeBasedEpochGenerator().generate();
      return;
    }
    this.id = id;
  }

  public UUID getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    IdentifiableDomainObject entity = (IdentifiableDomainObject) o;
    return Objects.equals(id, entity.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
