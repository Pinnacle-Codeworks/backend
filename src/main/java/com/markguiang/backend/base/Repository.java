package com.markguiang.backend.base;

import java.util.Optional;
import java.util.UUID;

public interface Repository<E extends Entity> {
  UUID save(E e);

  Optional<E> findByID(UUID ID);
}
