package com.markguiang.backend.event;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.service.EventService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //TODO issue where eventId can be set from request
    @PostMapping("")
    public Event upsertEvent(@RequestBody Event event) {
        return this.eventService.upsertEvent(event);
    }
}
