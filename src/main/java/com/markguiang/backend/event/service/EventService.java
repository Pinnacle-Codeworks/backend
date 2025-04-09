package com.markguiang.backend.event.service;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.model.Schedule;
import com.markguiang.backend.event.repository.EventRepository;
import com.markguiang.backend.event.repository.ScheduleRepository;
import com.markguiang.backend.exceptions.UniqueConstraintViolationException;
import com.markguiang.backend.user.UserContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    public final EventRepository eventRepository;
    public final ScheduleRepository scheduleRepository;
    public final UserContext userContext;
    public final ScheduleService scheduleService;

    public EventService(EventRepository eventRepository, UserContext userContext, ScheduleRepository scheduleRepository, ScheduleService scheduleService) {
        this.eventRepository = eventRepository;
        this.scheduleRepository =   scheduleRepository;
        this.userContext = userContext;
        this.scheduleService = scheduleService;
    }

    public Event createEventWithScheduleList(Event event) {
        try {
            //TODO intercept all model saves with companyid instaed of manual
            event.setCompanyId(userContext.getUser().getCompanyId());
            eventRepository.save(event);
            List<Schedule> scheduleList = scheduleService.createScheduleList(event);
            event.setScheduleList(scheduleList);
            return event;
        } catch (DataIntegrityViolationException ex) {
            if (eventRepository.existsByNameAndCompanyId(event.getName(), event.getCompanyId())) {
                throw new UniqueConstraintViolationException(event.getName());
            }
        }
        return null;
    }

    public Event updateEvent(Event event) {
        try {
            if (!userContext.getUser().getCompanyId().equals(event.getCompanyId())) {
               throw new AuthorizationDeniedException("Not allowed");
            }
            eventRepository.save(event);
            List<Schedule> scheduleList = scheduleService.updateScheduleList(event);
            event.setScheduleList(scheduleList);
            return event;
        } catch (DataIntegrityViolationException ex) {
            if (eventRepository.existsByNameAndCompanyId(event.getName(), event.getCompanyId())) {
                throw new UniqueConstraintViolationException(event.getName());
            }
        }
        return null;
    }
}