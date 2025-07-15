package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.event.domain.enums.EventSortBy;
import com.markguiang.backend.event.domain.enums.SortDirection;
import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.exceptions.DuplicateNameException;
import com.markguiang.backend.event.exceptions.EventDoesNotExistException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class EventService {
  private final EventRepository er;

  public EventService(EventRepository er) {
    this.er = er;
  }

  private Event getEventOrThrow(UUID eventID) {
    return er.findByID(eventID).orElseThrow(() -> new EventDoesNotExistException(eventID));
  }

  public Event getEvent(UUID eventID) {
    return getEventOrThrow(eventID);
  }

  public Page<Event> getEvents(int page, int size, EventSortBy sortBy, SortDirection direction) {
    String sortColumn = sortBy.getColumnName();
    String sortDirectionSql = direction.getSql();

    long totalCount = er.countEvents();
    int offset = page * size;

    List<Event> events = er.findEventsWithPagination(size, offset, sortColumn, sortDirectionSql);

    return new PageImpl<>(events, PageRequest.of(page, size), totalCount);
  }

  public UUID createEvent(Event event) {
    Boolean exists = er.existsByName(event.getName());
    if (exists) {
      throw new DuplicateNameException(event.getName());
    }

    return er.save(event);
  }

  public void updateEvent(Event event) {
    getEventOrThrow(event.getId());

    er.update(event);
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
