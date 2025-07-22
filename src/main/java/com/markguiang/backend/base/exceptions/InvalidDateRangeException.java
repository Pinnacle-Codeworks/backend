package com.markguiang.backend.base.exceptions;

public class InvalidDateRangeException extends ValidationException {
  public InvalidDateRangeException() {
    super("start-date-must-not-be-after-end-date");
  }
}