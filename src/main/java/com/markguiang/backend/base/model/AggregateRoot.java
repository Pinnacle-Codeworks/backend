package com.markguiang.backend.base.model;

import java.util.UUID;

public abstract class AggregateRoot extends Entity {
  protected AggregateRoot(UUID ID) {
    super(ID);
  }
}