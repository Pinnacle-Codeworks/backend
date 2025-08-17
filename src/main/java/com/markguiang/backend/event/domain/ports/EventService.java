package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.event.domain.enums.EventSortBy;
import com.markguiang.backend.event.domain.enums.SortDirection;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.exceptions.DuplicateNameException;
import com.markguiang.backend.event.exceptions.EventDoesNotExistException;
import java.io.IOException;
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

  public Event getEventOrThrow(UUID eventID) {
    return er.findByID(eventID).orElseThrow(() -> new EventDoesNotExistException(eventID));
  }

  public Event getEvent(UUID eventID) {
    return getEventOrThrow(eventID);
  }

  public Page<Event> getEventsWithoutDays(int page, int size, EventSortBy sortBy, SortDirection direction) {
    String sortColumn = sortBy.getColumnName();
    String sortDirectionSql = direction.getSql();

    long totalCount = er.countEvents();
    int offset = page * size;

    List<Event> events = er.findEventsWithoutDaysWithPagination(size, offset, sortColumn, sortDirectionSql);

    return new PageImpl<>(events, PageRequest.of(page, size), totalCount);
  }

  public UUID createEvent(Event event) {
    Boolean exists = er.existsByName(event.getName());
    if (exists) {
      throw new DuplicateNameException(event.getName());
    }

    return er.save(event);
  }

  public void updateEventDetails(UUID eventId, String description, String location) {
    Event event = getEventOrThrow(eventId);

    event.updateDetails(description, location);
    er.updateEventDetails(eventId, event.getDescription(), event.getLocation());
  }

  public void updateEventImage(UUID eventID, String imgUrl, String imgPath) throws IOException {
    Event event = getEventOrThrow(eventID);

    event.updateImage(imgUrl);
    event.updateImagePath(imgPath);
    er.updateImageUrl(eventID, event.getImgURL());
    er.updateImagePath(eventID, event.getImgPath());
  }

  public void updateEventImagePath(UUID eventID, String imgPath) throws IOException {
    Event event = getEventOrThrow(eventID);

    event.updateImagePath(imgPath);
    er.updateImagePath(eventID, event.getImgPath());
  }
}
