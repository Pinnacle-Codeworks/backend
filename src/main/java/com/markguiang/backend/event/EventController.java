package com.markguiang.backend.event;

import com.markguiang.backend.event.dto.request.CreateEventDTO;
import com.markguiang.backend.event.dto.request.UpdateEventDTO;
import com.markguiang.backend.event.dto.response.EventResponseDTO;
import com.markguiang.backend.event.mapper.EventMapper;
import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.service.EventService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PostMapping("")
    public EventResponseDTO createEvent(@RequestBody CreateEventDTO createEventDTO) {
        Event event = this.eventMapper.createEventDTOtoEvent(createEventDTO);
        Event eventResult = this.eventService.createEventWithScheduleList(event);
        return this.eventMapper.eventToEventResponseDTO(eventResult);
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PatchMapping("")
    public EventResponseDTO updateEvent(@Valid @RequestBody UpdateEventDTO updateEventDTO) {
        Event event = this.eventMapper.updateEventDTOtoEvent(updateEventDTO);
        Event eventResult =  this.eventService.updateEvent(event);
        return this.eventMapper.eventToEventResponseDTO(eventResult);
    }
}