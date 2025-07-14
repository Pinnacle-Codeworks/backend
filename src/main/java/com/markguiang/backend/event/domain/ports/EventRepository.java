package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.base.Repository;
import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends Repository<Event> {
  Boolean existsByName(String name);

  List<Event> findEventsWithPagination(int page, int size, String sortBy, String direction);

  int countEvents();

  void addAgenda(UUID dayId, Agenda agenda);

  void removeAgenda(UUID dayId, Agenda agenda);

  void updateAgenda(UUID dayId, Agenda agenda);

  void updateDay(Day day);
}
