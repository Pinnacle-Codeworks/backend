package com.markguiang.backend.tenant.exception;

import com.markguiang.backend.base.exceptions.EntityDoesNotExistException;

public class TenantDoesNotExistException extends EntityDoesNotExistException {
  public TenantDoesNotExistException(String name) {
    super("tenant", "name", name);
  }
}
