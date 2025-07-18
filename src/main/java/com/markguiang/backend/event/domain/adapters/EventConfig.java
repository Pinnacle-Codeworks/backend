package com.markguiang.backend.event.domain.adapters;

import com.markguiang.backend.event.domain.ports.EventRepository;
import com.markguiang.backend.event.domain.ports.EventService;
import com.markguiang.backend.infrastructure.storage.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

  @Bean
  public EventService eventService(EventRepository repository, StorageService storageService) {
    return new EventService(repository, storageService);
  }
}
