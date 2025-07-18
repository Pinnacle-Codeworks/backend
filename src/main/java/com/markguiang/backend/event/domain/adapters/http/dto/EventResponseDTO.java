package com.markguiang.backend.event.domain.adapters.http.dto;

import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record EventResponseDTO(
    UUID id,
    String name,
    Boolean hasMultipleLocation,
    String description,
    String location,
    URI imgURL,
    Event.EventStatus eventStatus,
    List<DayDTO> days) {

  public static EventResponseDTO fromEvent(Event event) {
    List<DayDTO> eventDays = Optional.ofNullable(event.getDays()).orElse(Collections.emptyList()).stream()
        .map(
            day -> new DayDTO(
                day.getId(),
                day.getDate(),
                day.getLocation(),
                day.getDescription(),
                new ArrayList<>()))
        .toList();

    return new EventResponseDTO(
        event.getId(),
        event.getName(),
        event.getHasMultipleLocation(),
        event.getDescription(),
        event.getLocation(),
        event.getImgURL(),
        event.getEventStatus(),
        eventDays);
  }

  public record DayDTO(
      UUID id,
      OffsetDateTime date,
      String location,
      String description,
      List<CreateUpdateAgendaDTO> agendas) {
    public static DayDTO fromDay(Day day) {
      List<CreateUpdateAgendaDTO> agendaDTOs = day.getAgendas().stream().map(CreateUpdateAgendaDTO::fromAgenda)
          .toList();

      return new DayDTO(
          day.getId(), day.getDate(), day.getLocation(), day.getDescription(), agendaDTOs);
    }
  }
}
