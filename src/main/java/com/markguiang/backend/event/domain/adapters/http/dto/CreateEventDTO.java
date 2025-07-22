package com.markguiang.backend.event.domain.adapters.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.base.annotation.ValidRequired;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record CreateEventDTO(
    @NotNull String name,
    String description,
    String location,
    Event.EventStatus status,
    Boolean hasMultipleLocation,
    @JsonProperty("days") @ValidRequired List<CreateUpdateDayDTO> days) {

  public static Event fromDTO(CreateEventDTO dto) {
    List<Day> days =
        Optional.ofNullable(dto.days()).orElse(Collections.emptyList()).stream()
            .map(dayDTO -> Day.createEmpty(dayDTO.location(), dayDTO.date(), dayDTO.description()))
            .toList();

    return Event.create(
        dto.name(),
        dto.hasMultipleLocation(),
        dto.description(),
        dto.location(),
        null,
        dto.status(),
        days);
  }
}
