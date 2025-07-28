package com.markguiang.backend.user.exception;

import com.markguiang.backend.base.exceptions.DomainException;

public class UserAlreadyExistsException extends DomainException {
  public UserAlreadyExistsException(String authId) {
    super("user-with-authId-" + authId + "-already-exists");
  }
}
