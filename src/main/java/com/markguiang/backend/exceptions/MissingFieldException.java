package com.markguiang.backend.exceptions;

public class MissingFieldException extends ValidationException {
  public MissingFieldException(String fieldName) {
    super(fieldName + "-must-not-be-null");
  }
}
