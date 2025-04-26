package com.markguiang.backend.event.service;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.model.Schedule;
import com.markguiang.backend.event.repository.EventRepository;
import com.markguiang.backend.event.repository.ScheduleRepository;
import com.markguiang.backend.exceptions.MissingFieldException;
import com.markguiang.backend.exceptions.UniqueConstraintViolationException;
import com.markguiang.backend.user.UserContext;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Event createEventWithScheduleList(Event event) {
        try {
            eventRepository.save(event);
            List<Schedule> scheduleList = scheduleService.createScheduleList(event);
            if (event.getScheduleList() != null) {
                for (Schedule schedule : event.getScheduleList()) {
                    if (schedule.getStartDate() == null || schedule.getEndDate() == null) {
                        throw new MissingFieldException("Each schedule must have a startDate and endDate");
                    }
                }
            }
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
}
