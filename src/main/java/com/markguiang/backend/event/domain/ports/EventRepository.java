package com.markguiang.backend.event.domain.ports;

import com.markguiang.backend.base.model.Repository;
import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Event;
import java.net.URI;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends Repository<Event> {
  Boolean existsByName(String name);

  List<Event> findEventsWithoutDaysWithPagination(int page, int size, String sortBy, String direction);

  void updateEventDetails(UUID eventId, String description, String location);

  int countEvents();

  void updateImageUrl(UUID eventId, String image);

  void updateImagePath(UUID eventId, String imagePath);

  void addAgenda(UUID dayId, Agenda agenda);

  void removeAgenda(UUID dayId, Agenda agenda);

  void updateAgenda(UUID dayId, Agenda agenda);

  void updateDayDetails(UUID dayId, String location, String description);
}
