package com.markguiang.backend.tenant.exception;

import com.markguiang.backend.base.exceptions.DomainException;

public class TenantAlreadyExistsException extends DomainException {
  public TenantAlreadyExistsException(String name) {
    super("tenant-with-name-" + name + "-already-exists");
  }
}