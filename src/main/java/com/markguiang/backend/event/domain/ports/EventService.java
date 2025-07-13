package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.exceptions.DuplicateNameException;
import com.markguiang.backend.event.exceptions.EventDoesNotExistException;
import java.util.UUID;

public class EventService {
  private EventRepository er;

  public EventService(EventRepository er) {
    this.er = er;
  }

  private Event getEventOrThrow(UUID eventID) {
    return er.findByID(eventID).orElseThrow(() -> new EventDoesNotExistException(eventID));
  }

  public UUID createEvent(Event event) {
    Boolean exists = er.existsByName(event.getName());
    if (exists) {
      throw new DuplicateNameException(event.getName());
    }

    UUID ID = er.save(event);
    return ID;
  }

  public void addAgenda(UUID eventID, Agenda agenda) {
    Event event = getEventOrThrow(eventID);

    Day day = event.addAgendaToDay(agenda);
    er.addAgenda(day.getId(), agenda);
  }

  public void removeAgenda(UUID eventID, Agenda agenda) {
    Event event = getEventOrThrow(eventID);

    Day day = event.removeAgendaFromDay(agenda);
    er.removeAgenda(day.getId(), agenda);
  }

  public void updateAgenda(UUID eventID, Agenda agenda) {
    Event event = getEventOrThrow(eventID);

    Day day = event.removeAgendaFromDay(agenda);
    event.addAgendaToDay(agenda);
    er.updateAgenda(day.getId(), agenda);
  }

  public void updateDay(UUID eventID, Day day) {
    Event event = getEventOrThrow(eventID);

    event.updateDay(day);
    er.updateDay(day);
  }
}
