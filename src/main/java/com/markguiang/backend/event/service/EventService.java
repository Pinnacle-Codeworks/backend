package com.markguiang.backend.event.service;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.model.Schedule;
import com.markguiang.backend.event.repository.EventRepository;
import com.markguiang.backend.event.repository.ScheduleRepository;
import com.markguiang.backend.exceptions.UniqueConstraintViolationException;
import com.markguiang.backend.user.UserContext;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    public final EventRepository eventRepository;
    public final ScheduleRepository scheduleRepository;
    public final UserContext userContext;
    public final ScheduleService scheduleService;

    public EventService(
            EventRepository eventRepository,
            UserContext userContext,
            ScheduleRepository scheduleRepository,
            ScheduleService scheduleService) {
        this.eventRepository = eventRepository;
        this.scheduleRepository = scheduleRepository;
        this.userContext = userContext;
        this.scheduleService = scheduleService;
    }

    public Event createEventWithScheduleList(Event event) {
        try {
            eventRepository.save(event);
            List<Schedule> scheduleList = scheduleService.createScheduleList(event);
            event.setScheduleList(scheduleList);
            return event;
        } catch (DataIntegrityViolationException ex) {
            if (eventRepository.existsByName(event.getName())) {
                throw new UniqueConstraintViolationException(event.getName());
            }
        }
        return null;
    }

    public Event updateEvent(Event event) {
        try {
            eventRepository.save(event);
            List<Schedule> scheduleList = scheduleService.updateScheduleList(event);
            event.setScheduleList(scheduleList);
            return event;
        } catch (DataIntegrityViolationException ex) {
            if (eventRepository.existsByName(event.getName())) {
                throw new UniqueConstraintViolationException(event.getName());
            }
        }
        // fix
        return null;
    }

    public Optional<Event> getEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }

    public Page<Event> getAllEvent(@Nullable String sortBy, @Nullable String direction, int page, int size) {
        Pageable pageable;
        if (sortBy != null && !sortBy.isBlank()) {
            Sort.Direction sortDirection =
                    "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        } else {
            pageable = PageRequest.of(page, size);
        }
        return eventRepository.findAll(pageable);
    }
}
