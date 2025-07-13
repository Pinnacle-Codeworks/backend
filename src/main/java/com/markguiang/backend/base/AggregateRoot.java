package com.markguiang.backend.base;

import java.util.UUID;

public abstract class AggregateRoot extends Entity {
  protected AggregateRoot() {
    super();
  }

  protected AggregateRoot(UUID ID) {
    super(ID);
  }
}
