package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import java.util.UUID;

public class DayService {
  private final EventService es;
  private final EventRepository er;

  public DayService(EventService es, EventRepository er) {
    this.es = es;
    this.er = er;
  }

  public void addAgenda(UUID eventID, Agenda agenda) {
    Event event = es.getEventOrThrow(eventID);

    Day day = event.addAgendaToDay(agenda);
    er.addAgenda(day.getId(), agenda);
  }

  public void removeAgenda(UUID eventID, Agenda agenda) {
    Event event = es.getEventOrThrow(eventID);

    Day day = event.removeAgendaFromDay(agenda);
    er.removeAgenda(day.getId(), agenda);
  }

  public void updateAgenda(UUID eventID, Agenda agenda) {
    Event event = es.getEventOrThrow(eventID);

    event.removeAgendaFromDay(agenda);
    Day newDay = event.addAgendaToDay(agenda);
    er.updateAgenda(newDay.getId(), agenda);
  }

  public void updateDayDetails(UUID eventID, Day day) {
    Event event = es.getEventOrThrow(eventID);

    event.updateDayDetails(day.getId(), day.getDate(), day.getLocation(), day.getDescription());
    er.updateDayDetails(day.getId(), day.getLocation(), day.getDescription());
  }
}
