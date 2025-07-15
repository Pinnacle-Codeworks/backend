package com.markguiang.backend.event.domain.adapters.http.dto;

import com.markguiang.backend.event.domain.models.Agenda;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record CreateUpdateAgendaDTO(
    @NotNull OffsetDateTime startDate,
    @NotNull OffsetDateTime endDate,
    String location) {
  public static Agenda fromDTO(CreateUpdateAgendaDTO agendaDTO) {
    return new Agenda(agendaDTO.startDate(), agendaDTO.endDate(), agendaDTO.location());
  }

  public static CreateUpdateAgendaDTO fromAgenda(Agenda agenda) {
    return new CreateUpdateAgendaDTO(agenda.getStartDate(), agenda.getEndDate(), agenda.getLocation());
  }
}