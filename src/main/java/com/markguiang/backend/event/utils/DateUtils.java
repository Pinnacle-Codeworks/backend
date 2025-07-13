package com.markguiang.backend.event.utils;

import java.time.OffsetDateTime;

public class DateUtils {
  public static boolean onSameDate(OffsetDateTime a, OffsetDateTime b) {
    return a.toLocalDate().equals(b.toLocalDate());
  }
}
