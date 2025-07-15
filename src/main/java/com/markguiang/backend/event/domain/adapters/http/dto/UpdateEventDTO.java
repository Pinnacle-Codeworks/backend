package com.markguiang.backend.event.domain.adapters.http.dto;

import com.markguiang.backend.event.domain.models.Event;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.UUID;

public record UpdateEventDTO(
    @NotNull UUID id,
    @NotNull String name,
    String description,
    String location,
    String imgURL,
    Event.EventStatus eventStatus,
    Boolean hasMultipleLocation) {

  public static Event fromDTO(UpdateEventDTO dto) {
    return new Event(
        dto.id(),
        dto.name(),
        dto.hasMultipleLocation(),
        dto.description(),
        dto.location(),
        dto.imgURL(),
        dto.eventStatus(),
        new ArrayList<>());
  }
}
