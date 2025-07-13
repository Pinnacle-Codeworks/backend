package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.base.Repository;
import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Event;
import java.util.UUID;

public interface EventRepository extends Repository<Event> {
  Boolean existsByName(String name);

  void addAgenda(UUID eventID, UUID dayID, Agenda agenda);

  void deleteAgenda(UUID eventID, UUID dayID, Agenda agenda);
}
