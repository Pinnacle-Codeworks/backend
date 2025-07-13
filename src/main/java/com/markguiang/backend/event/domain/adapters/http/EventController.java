package com.markguiang.backend.event.domain.adapters.http;

import com.markguiang.backend.event.domain.adapters.http.dto.CreateEventDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.EventResponseDTO;
import com.markguiang.backend.event.domain.adapters.http.dto.UpdateEventDTO;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.domain.ports.EventService;
import jakarta.validation.Valid;
import java.util.UUID;
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

  // @GetMapping("/all")
  // public Page<EventResponseDTO> getAllEvent(
  // @RequestParam(required = false) String sortBy,
  // @RequestParam(required = false) String direction,
  // @RequestParam(defaultValue = "0") int page, // Default to page 0
  // @RequestParam(defaultValue = "10") int size // Default to 10 items per page
  // ) {
  // Page<Event> eventResult = this.eventService.getAllEvent(sortBy, direction,
  // page, size);
  // return eventResult.map(eventResponseMapper::eventToEventResponseDTO);
  // }

  @GetMapping("")
  public EventResponseDTO getEvent(@RequestParam UUID id) {
    Event event = this.eventService.getEvent(id);
    return EventResponseDTO.fromEvent(event);
  }
}
