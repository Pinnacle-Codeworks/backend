package com.markguiang.backend.event;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.service.EventService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("")
    public Event upsertEvent(@RequestBody Event event) {
        return this.eventService.upsertEvent(event);
    }
}
