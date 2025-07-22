package com.markguiang.backend.event.domain.enums;

public enum EventSortBy {
  ID("id"),
  NAME("name"),
  STATUS("status"),
  LOCATION("location"),
  DESCRIPTION("description"),
  HAS_MULTIPLE_LOCATION("has_multiple_location");

  private final String columnName;

  EventSortBy(String columnName) {
    this.columnName = columnName;
  }

  public String getColumnName() {
    return columnName;
  }

  public static EventSortBy fromString(String value) {
    if (value == null) {
      return ID;
    }

    try {
      return EventSortBy.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ID;
    }
  }
}
