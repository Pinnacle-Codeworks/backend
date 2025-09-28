package com.markguiang.backend.event.utils;

import java.time.OffsetDateTime;

public class Utils {
  public static boolean onSameDate(OffsetDateTime a, OffsetDateTime b) {
    return a.toLocalDate().equals(b.toLocalDate());
  }

  public static String concatenateStr(String... s) {
    StringBuilder sb = new StringBuilder();
    for (String string : s) {
      sb.append(string);
    }

    return sb.toString();
  }
}
