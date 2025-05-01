package com.markguiang.backend.event;

import com.markguiang.backend.event.dto.request.CreateEventDTO;
import com.markguiang.backend.event.dto.request.UpdateEventDTO;
import com.markguiang.backend.event.dto.response.EventResponseDTO;
import com.markguiang.backend.event.dto.mapper.EventRequestMapper;
import com.markguiang.backend.event.dto.mapper.EventResponseMapper;
import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.service.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    private final EventRequestMapper eventRequestMapper;
    private final EventResponseMapper eventResponseMapper;

    public EventController(EventService eventService, EventRequestMapper eventRequestMapper, EventResponseMapper eventResponseMapper) {
        this.eventService = eventService;
        this.eventRequestMapper = eventRequestMapper;
        this.eventResponseMapper = eventResponseMapper;
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PostMapping("")
    public EventResponseDTO createEvent(@Valid @RequestBody CreateEventDTO createEventDTO) {
        Event event = this.eventRequestMapper.createEventDTOtoEvent(createEventDTO);
        Event eventResult = this.eventService.createEventWithScheduleList(event);
        return this.eventResponseMapper.eventToEventResponseDTO(eventResult);
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PatchMapping("")
    public EventResponseDTO updateEvent(@Valid @RequestBody UpdateEventDTO updateEventDTO) {
        Event event = this.eventRequestMapper.updateEventDTOtoEvent(updateEventDTO);
        Event eventResult =  this.eventService.updateEvent(event);
        return this.eventResponseMapper.eventToEventResponseDTO(eventResult);
    }

    @GetMapping("/all")
    public Page<EventResponseDTO> getAllEvent(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction,
            @RequestParam(defaultValue = "0") int page, // Default to page 0
            @RequestParam(defaultValue = "10") int size // Default to 10 items per page
    ) {
        Page<Event> eventResult = this.eventService.getAllEvent(sortBy, direction, page, size);
        return eventResult.map(eventResponseMapper::eventToEventResponseDTO);
    }
    
    @GetMapping("")
    public EventResponseDTO getEvent(@RequestParam long id) {
        Optional<Event> eventResult = this.eventService.getEvent(id);
        return this.eventResponseMapper.eventToEventResponseDTO(
                eventResult.orElseThrow(() -> new RuntimeException("Event not found"))
        );
    }
}
