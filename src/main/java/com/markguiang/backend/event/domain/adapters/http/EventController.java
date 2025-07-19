package com.markguiang.backend.event.domain.adapters.http;

import com.markguiang.backend.event.domain.adapters.http.dto.CreateEventDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.CreateUpdateAgendaDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.CreateUpdateDayDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.EventResponseDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.EventResponseWithoutDaysDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.UpdateEventDTO;
import com.markguiang.backend.event.domain.enums.EventSortBy;
import com.markguiang.backend.event.domain.enums.SortDirection;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.domain.ports.EventService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("")
  public Page<EventResponseWithoutDaysDTO> getEvents(
      @RequestParam(required = false) EventSortBy sortBy,
      @RequestParam(required = false) SortDirection direction,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    EventSortBy eventSortBy = sortBy != null ? sortBy : EventSortBy.ID;
    SortDirection sortDirection = direction != null ? direction : SortDirection.ASC;

    Page<Event> events = this.eventService.getEvents(page, size, eventSortBy, sortDirection);
    return events.map(EventResponseWithoutDaysDTO::fromEvent);
  }

  @GetMapping("/{eventId}")
  public EventResponseDTO getEvent(@PathVariable UUID eventId) {
    Event event = this.eventService.getEvent(eventId);
    return EventResponseDTO.fromEvent(event);
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @PostMapping("/agenda/{eventId}")
  public void addAgenda(
      @PathVariable UUID eventId, @Valid @RequestBody CreateUpdateAgendaDTO agendaDTO) {
    eventService.addAgenda(eventId, CreateUpdateAgendaDTO.fromDTO(agendaDTO));
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @PatchMapping("/agenda/{eventId}")
  public void updateAgenda(
      @PathVariable UUID eventId, @Valid @RequestBody CreateUpdateAgendaDTO agendaDTO) {
    eventService.updateAgenda(eventId, CreateUpdateAgendaDTO.fromDTO(agendaDTO));
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @DeleteMapping("/agenda/{eventId}")
  public void deleteAgenda(
      @PathVariable UUID eventId, @Valid @RequestBody CreateUpdateAgendaDTO agendaDTO) {
    eventService.removeAgenda(eventId, CreateUpdateAgendaDTO.fromDTO(agendaDTO));
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @PatchMapping("/day/{eventId}")
  public void updateDay(@PathVariable UUID eventId, @Valid @RequestBody CreateUpdateDayDTO dayDTO) {
    eventService.updateDay(eventId, CreateUpdateDayDTO.fromDTO(dayDTO));
  }

  @PreAuthorize("hasAuthority('permission:write')")
  @PostMapping("/image-url/{eventId}")
  public void updateImageUrl(@PathVariable UUID eventId, URI imageUrl) throws IOException {
    eventService.updateImageUrl(eventId, imageUrl);
  }
}
