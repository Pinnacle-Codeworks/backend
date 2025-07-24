package com.markguiang.backend.event.domain.adapters;

import com.markguiang.backend.event.domain.ports.DayService;
import com.markguiang.backend.event.domain.ports.EventRepository;
import com.markguiang.backend.event.domain.ports.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

  @Bean
  public EventService eventService(EventRepository repository) {
    return new EventService(repository);
  }

  @Bean
  public DayService dayService(EventService eventService, EventRepository repository) {
    return new DayService(eventService, repository);
  }
}
