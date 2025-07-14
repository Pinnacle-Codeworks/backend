package com.markguiang.backend.event.domain.adapters.http;

import com.markguiang.backend.event.domain.adapters.http.dto.CreateEventDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.EventResponseDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.EventResponseWithoutDaysDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.UpdateEventDTO;
import com.markguiang.backend.event.domain.enums.EventSortBy;
import com.markguiang.backend.event.domain.enums.SortDirection;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.domain.ports.EventService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {
  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @PostMapping("")
  public UUID createEvent(@Valid @RequestBody CreateEventDTO createEventDTO) {
    Event event = CreateEventDTO.fromDTO(createEventDTO);
    return eventService.createEvent(event);
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @PatchMapping("")
  public void updateEvent(@Valid @RequestBody UpdateEventDTO updateEventDTO) {
    Event event = UpdateEventDTO.fromDTO(updateEventDTO);
    eventService.updateEvent(event);
  }

  @GetMapping("/all")
  public Page<EventResponseWithoutDaysDTO> getAllEvent(
      @RequestParam(required = false) EventSortBy sortBy,
      @RequestParam(required = false) SortDirection direction,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    EventSortBy eventSortBy = sortBy != null ? sortBy : EventSortBy.ID;
    SortDirection sortDirection = direction != null ? direction : SortDirection.ASC;

    Page<Event> events = this.eventService.getEvents(page, size, eventSortBy, sortDirection);
    return events.map(event -> EventResponseWithoutDaysDTO.fromEvent(event));
  }

  @GetMapping("")
  public EventResponseDTO getEvent(@RequestParam UUID id) {
    Event event = this.eventService.getEvent(id);
    return EventResponseDTO.fromEvent(event);
  }
}
