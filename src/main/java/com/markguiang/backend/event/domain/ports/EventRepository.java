package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.base.Repository;
import com.markguiang.backend.event.domain.models.Event;

public interface EventRepository extends Repository<Event, Long> {
}
