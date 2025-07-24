package com.markguiang.backend.user.exception;

import com.markguiang.backend.base.exceptions.DomainException;

public class UserNotFound extends DomainException {
  public UserNotFound(String authId) {
    super("user-with-authId-" + authId + "-not-found");
  }
}
