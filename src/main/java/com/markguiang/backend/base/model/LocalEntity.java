package com.markguiang.backend.base.model;

import java.util.UUID;

public abstract class LocalEntity extends Entity {
  protected LocalEntity(UUID ID) {
    super(ID);
  }
}