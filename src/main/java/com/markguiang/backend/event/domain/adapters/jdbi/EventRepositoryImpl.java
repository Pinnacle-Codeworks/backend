package com.markguiang.backend.event.domain.adapters.jdbi;

import com.fasterxml.uuid.Generators;
import com.markguiang.backend.event.domain.adapters.jdbi.mappers.AgendaInsertDto;
import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.domain.ports.EventRepository;
import com.markguiang.backend.tenant.TenantContext;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EventRepositoryImpl implements EventRepository {

  private final EventDao dao;
  private final Long tenantId = TenantContext.getTenantId();

  public EventRepositoryImpl(EventDao dao) {
    this.dao = dao;
  }

  @Override
  public List<Event> findEventsWithPagination(
      int size, int offset, String sortColumn, String sortDirection) {
    return dao.findEventsWithPagination(tenantId, size, offset, sortColumn, sortDirection);
  }

  @Override
  public int countEvents() {
    return dao.countEvents(tenantId);
  }

  @Override
  public void updateImage(UUID eventId, URI image) {
    dao.updateImage(tenantId, eventId, image);
  }

  @Override
  @Transactional
  // List<Day> -> List<Agenda> not included
  public UUID save(Event event) {
    dao.insertEvent(tenantId, event);

    for (Day day : event.getDays()) {
      dao.insertDay(
          tenantId,
          day.getId(),
          event.getId(),
          day.getLocation(),
          day.getDate(),
          day.getDescription());
    }

    return event.getId();
  }

  @Override
  public void update(Event event) {
    dao.updateEvent(tenantId, event);
  }

  @Override
  public Optional<Event> findByID(UUID id) {
    return dao.findEventAggregate(tenantId, id);
  }

  @Override
  public Boolean existsByName(String name) {
    return dao.existsByName(tenantId, name);
  }

  @Override
  public void addAgenda(UUID dayId, Agenda agenda) {
    AgendaInsertDto agendaDTO = new AgendaInsertDto(
        Generators.timeBasedEpochGenerator().generate(),
        dayId,
        agenda.getStartDate(),
        agenda.getEndDate(),
        agenda.getLocation());
    dao.insertAgenda(tenantId, agendaDTO);
  }

  @Override
  public void removeAgenda(UUID dayId, Agenda agenda) {
    dao.removeAgenda(tenantId, dayId, agenda.getStartDate(), agenda.getEndDate());
  }

  @Override
  @Transactional
  public void updateAgenda(UUID dayId, Agenda agenda) {
    AgendaInsertDto agendaDTO = new AgendaInsertDto(
        Generators.timeBasedEpochGenerator().generate(),
        dayId,
        agenda.getStartDate(),
        agenda.getEndDate(),
        agenda.getLocation());
    dao.removeAgenda(tenantId, dayId, agendaDTO.getStartDate(), agendaDTO.getEndDate());
    dao.insertAgenda(tenantId, agendaDTO);
  }

  @Override
  public void updateDay(Day day) {
    dao.updateDay(tenantId, day.getId(), day.getLocation(), day.getDescription());
  }
}
