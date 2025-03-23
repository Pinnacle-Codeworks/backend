package com.markguiang.backend.event.repository;

import com.markguiang.backend.event.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
    Boolean existsByName(String name);

}
