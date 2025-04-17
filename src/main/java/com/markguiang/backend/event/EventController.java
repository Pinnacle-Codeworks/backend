package com.markguiang.backend.event;

import com.markguiang.backend.event.dto.request.CreateEventDTO;
import com.markguiang.backend.event.dto.request.UpdateEventDTO;
import com.markguiang.backend.event.dto.response.EventResponseDTO;
import com.markguiang.backend.event.dto.mapper.EventRequestMapper;
import com.markguiang.backend.event.dto.mapper.EventResponseMapper;
import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.service.EventService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
