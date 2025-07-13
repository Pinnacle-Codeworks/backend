package com.markguiang.backend.base;

import java.util.UUID;

public abstract class LocalEntity extends Entity {
  protected LocalEntity() {
    super();
  }

  protected LocalEntity(UUID ID) {
    super(ID);
  }
}
