package com.markguiang.backend.base.model;

import java.util.Optional;
import java.util.UUID;

public interface Repository<E extends Entity> {
  UUID save(E e);

  void update(E e);

  Optional<E> findByID(UUID ID);
}