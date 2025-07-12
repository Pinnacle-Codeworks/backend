package com.markguiang.backend.base;

public interface Repository<E extends Entity<IDType>, IDType> {
  E save(E e);

  E get(IDType ID);
}
