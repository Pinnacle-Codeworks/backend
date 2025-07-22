package com.markguiang.backend.base.model;

import com.fasterxml.uuid.Generators;
import com.markguiang.backend.base.exceptions.DuplicateIdInCollectionException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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

  public static void validateForDuplicateKeys(Collection<? extends IdentifiableDomainObject> idos) {
    Set<UUID> ids = new HashSet<>();
    for (IdentifiableDomainObject ido : idos) {
      if (!ids.add(ido.id)) {
        throw new DuplicateIdInCollectionException();
      }
    }
  }
}
