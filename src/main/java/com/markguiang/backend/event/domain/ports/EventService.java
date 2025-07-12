package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.event.domain.models.Event;

public class EventService {
  private EventRepository er;

  public EventService(EventRepository er) {
    this.er = er;
  }

  public void createEvent(Event event) {
    er.save(event);
  }
}
