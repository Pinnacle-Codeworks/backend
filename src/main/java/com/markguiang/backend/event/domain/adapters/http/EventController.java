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
import com.markguiang.backend.event.domain.ports.DayService;
import com.markguiang.backend.event.domain.ports.EventService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.markguiang.backend.utils.Utils.concatenateStr;

@RestController
@RequestMapping("/event")
public class EventController {
  @Value("${CDN_URL}")
  private String cdnUrl;
  private final EventService eventService;
  private final DayService dayService;

  public EventController(EventService eventService, DayService dayService) {
    this.eventService = eventService;
    this.dayService = dayService;
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
  @PostMapping("")
  public UUID createEvent(@Valid @RequestBody CreateEventDTO createEventDTO) {
    Event event = CreateEventDTO.fromDTO(createEventDTO);
    return eventService.createEvent(event);
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
  @PutMapping("")
  public void updateEventDetails(@Valid @RequestBody UpdateEventDTO updateEventDTO) {
    eventService.updateEventDetails(
        updateEventDTO.id(), updateEventDTO.description(), updateEventDTO.location());
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).READ.name())")
  @GetMapping("")
  public Page<EventResponseWithoutDaysDTO> getEvents(
      @RequestParam(required = false) EventSortBy sortBy,
      @RequestParam(required = false) SortDirection direction,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    EventSortBy eventSortBy = sortBy != null ? sortBy : EventSortBy.ID;
    SortDirection sortDirection = direction != null ? direction : SortDirection.ASC;

    Page<Event> events = this.eventService.getEventsWithoutDays(page, size, eventSortBy, sortDirection);
    return events.map(EventResponseWithoutDaysDTO::fromEvent);
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).READ.name())")
  @GetMapping("/{eventId}")
  public EventResponseDTO getEvent(@PathVariable UUID eventId) {
    Event event = this.eventService.getEvent(eventId);
    return EventResponseDTO.fromEvent(event);
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
  @PostMapping("/agenda/{eventId}")
  public void addAgenda(
      @PathVariable UUID eventId, @Valid @RequestBody CreateUpdateAgendaDTO agendaDTO) {
    dayService.addAgenda(eventId, CreateUpdateAgendaDTO.fromDTO(agendaDTO));
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
  @PutMapping("/agenda/{eventId}")
  public void updateAgenda(
      @PathVariable UUID eventId, @Valid @RequestBody CreateUpdateAgendaDTO agendaDTO) {
    dayService.updateAgenda(eventId, CreateUpdateAgendaDTO.fromDTO(agendaDTO));
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
  @DeleteMapping("/agenda/{eventId}")
  public void deleteAgenda(
      @PathVariable UUID eventId, @Valid @RequestBody CreateUpdateAgendaDTO agendaDTO) {
    dayService.removeAgenda(eventId, CreateUpdateAgendaDTO.fromDTO(agendaDTO));
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
  @PutMapping("/day/{eventId}")
  public void updateDayDetails(
      @PathVariable UUID eventId, @Valid @RequestBody CreateUpdateDayDTO dayDTO) {
    dayService.updateDayDetails(eventId, CreateUpdateDayDTO.fromDTO(dayDTO));
  }


  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).WRITE.name())")
  @PutMapping("/image-url/{eventId}")
  public void updateGCSImageUrl(@PathVariable UUID eventId, @RequestParam String path) throws IOException {
    String imageUrl = concatenateStr(cdnUrl, path);
    eventService.updateEventImage(eventId, imageUrl, path);
  }
}
