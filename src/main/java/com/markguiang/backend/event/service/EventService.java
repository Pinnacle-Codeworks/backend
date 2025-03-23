package com.markguiang.backend.event.service;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.repository.EventRepository;
import com.markguiang.backend.exceptions.UniqueConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    public final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event upsertEvent(@Valid Event event) {
        try {
            return eventRepository.save(event);
        } catch (DataIntegrityViolationException ex) {
            if (eventRepository.existsByName(event.getName())) {
                throw new UniqueConstraintViolationException(event.getName());
            }
        }
        return event;
    }
}
