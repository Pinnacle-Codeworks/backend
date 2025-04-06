package com.markguiang.backend.event.service;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.model.Schedule;
import com.markguiang.backend.event.repository.EventRepository;
import com.markguiang.backend.event.repository.ScheduleRepository;
import com.markguiang.backend.exceptions.UniqueConstraintViolationException;
import com.markguiang.backend.user.UserContext;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    public final EventRepository eventRepository;
    public final ScheduleRepository scheduleRepository;
    public final UserContext userContext;

    public EventService(EventRepository eventRepository, UserContext userContext, ScheduleRepository scheduleRepository) {
        this.eventRepository = eventRepository;
        this.scheduleRepository =   scheduleRepository;
        this.userContext = userContext;
    }

    public Event createEvent(@Valid Event event) {
        try {
            event.clearIds();
            event.setCompanyId(userContext.getUser().getCompanyId());
            eventRepository.save(event);

            List<Schedule> scheduleList = event.getScheduleList();
            for (Schedule schedule: scheduleList) {
                schedule.setEventId(event.getEventId());
            }
            scheduleRepository.saveAll(scheduleList);

            return event;
        } catch (DataIntegrityViolationException ex) {
            if (eventRepository.existsByName(event.getName())) {
                throw new UniqueConstraintViolationException(event.getName());
            }
        }
        return null;
    }

    public Event updateEvent(@Valid Event event) {
        try {
            if (!userContext.getUser().getCompanyId().equals(event.getCompanyId())) {
               throw new AuthorizationDeniedException("Not allowed");
            }
            List<Schedule>  scheduleList = event.getScheduleList();
            for (Schedule schedule: scheduleList) {
                schedule.setEventId(event.getEventId());
            }
            return eventRepository.save(event);
        } catch (DataIntegrityViolationException ex) {
            if (eventRepository.existsByName(event.getName())) {
                throw new UniqueConstraintViolationException(event.getName());
            }
        }
        return null;
    }
}
