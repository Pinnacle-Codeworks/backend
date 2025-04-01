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

    @PostMapping("")
    public Event createEvent(@RequestBody Event event) {
        event.clearIds();
        return this.eventService.upsertEvent(event);
    }

    @PatchMapping("")
    public Event updateEvent(@RequestBody Event event) {
        return this.eventService.upsertEvent(event);
    }
}
