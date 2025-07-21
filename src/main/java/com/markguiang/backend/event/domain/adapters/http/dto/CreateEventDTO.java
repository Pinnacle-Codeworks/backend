package com.markguiang.backend.event.domain.adapters.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.markguiang.backend.base.annotation.ValidRequired;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record CreateEventDTO(
    @NotNull String name,
    String description,
    String location,
    Event.EventStatus eventStatus,
    Boolean hasMultipleLocation,
    @JsonProperty("days") @ValidRequired List<CreateUpdateDayDTO> days) {

  public static Event fromDTO(CreateEventDTO dto) {
    List<Day> eventDays =
        Optional.ofNullable(dto.days()).orElse(Collections.emptyList()).stream()
            .map(
                dayDTO ->
                    new Day(
                        dayDTO.location(), dayDTO.date(), new ArrayList<>(), dayDTO.description()))
            .toList();

    return new Event(
        dto.name(),
        dto.hasMultipleLocation(),
        dto.description(),
        dto.location(),
        null,
        dto.eventStatus(),
        eventDays);
  }
}
