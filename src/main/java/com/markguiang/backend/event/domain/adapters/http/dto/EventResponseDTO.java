package com.markguiang.backend.event.domain.adapters.http.dto;

import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record EventResponseDTO(
    UUID id,
    String name,
    Boolean hasMultipleLocation,
    String description,
    String location,
    String imgURL,
    Event.EventStatus eventStatus,
    List<DayDTO> days) {

  public static EventResponseDTO fromEvent(Event event) {
    List<DayDTO> dayDTOs = event.getDays().stream().map(DayDTO::fromDay).toList();

    return new EventResponseDTO(
        event.getId(),
        event.getName(),
        event.getHasMultipleLocation(),
        event.getDescription(),
        event.getLocation(),
        event.getImgURL(),
        event.getEventStatus(),
        dayDTOs);
  }

  public record DayDTO(
      UUID id, OffsetDateTime date, String location, String description, List<AgendaDTO> agendas) {
    public static DayDTO fromDay(Day day) {
      List<AgendaDTO> agendaDTOs = day.getAgendas().stream().map(AgendaDTO::fromAgenda).toList();

      return new DayDTO(
          day.getId(), day.getDate(), day.getLocation(), day.getDescription(), agendaDTOs);
    }
  }

  public record AgendaDTO(OffsetDateTime startDate, OffsetDateTime endDate, String location) {
    public static AgendaDTO fromAgenda(Agenda agenda) {
      return new AgendaDTO(agenda.getStartDate(), agenda.getEndDate(), agenda.getLocation());
    }
  }
}
