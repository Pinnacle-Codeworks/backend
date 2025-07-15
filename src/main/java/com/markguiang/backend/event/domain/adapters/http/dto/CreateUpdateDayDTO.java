package com.markguiang.backend.event.domain.adapters.http.dto;

import com.markguiang.backend.event.domain.models.Day;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public record CreateUpdateDayDTO(
        String location,
        @NotNull OffsetDateTime date,
        String description) {
  public static Day fromDTO(CreateUpdateDayDTO dayDTO) {
    return new Day(dayDTO.location(), dayDTO.date(), new ArrayList<>(), dayDTO.description());
  }

  public static CreateUpdateDayDTO fromDay(Day day) {
    return new CreateUpdateDayDTO(day.getLocation(), day.getDate(), day.getDescription());
  }
}