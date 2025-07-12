package com.markguiang.backend.exceptions;

public class InvalidDateRangeException extends ValidationException {
  public InvalidDateRangeException() {
    super("start-date-must-not-be-after-end-date");
  }
}
