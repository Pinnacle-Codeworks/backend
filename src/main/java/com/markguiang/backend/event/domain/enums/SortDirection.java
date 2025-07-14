package com.markguiang.backend.event.domain.enums;

public enum SortDirection {
  ASC("ASC"),
  DESC("DESC");

  private final String sql;

  SortDirection(String sql) {
    this.sql = sql;
  }

  public String getSql() {
    return sql;
  }

  public static SortDirection fromString(String value) {
    if (value == null) {
      return ASC;
    }

    try {
      return SortDirection.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ASC;
    }
  }
}
