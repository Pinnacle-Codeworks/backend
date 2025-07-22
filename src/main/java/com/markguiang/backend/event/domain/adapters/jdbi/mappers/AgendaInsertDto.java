package com.markguiang.backend.event.domain.adapters.jdbi.mappers;

import java.time.OffsetDateTime;
import java.util.UUID;

public class AgendaInsertDto {
  private final UUID id;
  private final UUID dayId;
  private final OffsetDateTime startDate;
  private final OffsetDateTime endDate;
  private final String location;

  public AgendaInsertDto(
      UUID id, UUID dayId, OffsetDateTime startDate, OffsetDateTime endDate, String location) {
    this.id = id;
    this.dayId = dayId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.location = location;
  }

  public UUID getId() {
    return id;
  }

  public UUID getDayId() {
    return dayId;
  }

  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public OffsetDateTime getEndDate() {
    return endDate;
  }

  public String getLocation() {
    return location;
  }
}
