package com.markguiang.backend.base;

import java.util.UUID;

public abstract class AggregateRoot extends Entity {
  protected AggregateRoot(UUID ID) {
    super(ID);
  }
}
