package com.markguiang.backend.event.domain.adapters.jdbi;

import com.fasterxml.uuid.Generators;
import com.markguiang.backend.event.domain.adapters.jdbi.mappers.AgendaInsertDto;
import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.domain.ports.EventRepository;
import com.markguiang.backend.infrastructure.auth.context.TenantContext;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EventRepositoryImpl implements EventRepository {

  private final EventDao dao;
  private final TenantContext tenantContext;

  public EventRepositoryImpl(EventDao dao, TenantContext tenantContext) {
    this.dao = dao;
    this.tenantContext = tenantContext;
  }

  @Override
  public List<Event> findEventsWithoutDaysWithPagination(
      int size, int offset, String sortColumn, String sortDirection) {
    UUID tenantId = tenantContext.getTenantId();
    return dao.findEventsWithoutDaysWithPagination(tenantId, size, offset, sortColumn, sortDirection);
  }

  @Override
  public void updateEventDetails(UUID eventId, String description, String location) {
    UUID tenantId = tenantContext.getTenantId();
    dao.updateEventDetails(tenantId, eventId, description, location);
  }

  @Override
  public int countEvents() {
    UUID tenantId = tenantContext.getTenantId();
    return dao.countEvents(tenantId);
  }

  @Override
  public void updateImageUrl(UUID eventId, String image) {
    UUID tenantId = tenantContext.getTenantId();
    dao.updateImageUrl(tenantId, eventId, image);
  }

  @Override
  public void updateImagePath(UUID eventId, String imagePath) {
    UUID tenantId = tenantContext.getTenantId();
    dao.updateImagePath(tenantId, eventId, imagePath);
  }

  @Override
  @Transactional
  // List<Day> -> List<Agenda> not included
  public UUID save(Event event) {
    UUID tenantId = tenantContext.getTenantId();
    dao.insertEvent(tenantId, event);

    List<Day> days = event.getDays();
    List<UUID> dayIds = days.stream().map(Day::getId).toList();
    List<String> locations = days.stream().map(Day::getLocation).toList();
    List<OffsetDateTime> dates = days.stream().map(Day::getDate).toList();
    List<String> descriptions = days.stream().map(Day::getDescription).toList();

    dao.insertDay(tenantId, dayIds, event.getId(), locations, dates, descriptions);

    return event.getId();
  }

  @Override
  public Optional<Event> findByID(UUID id) {
    UUID tenantId = tenantContext.getTenantId();
    return dao.findEventAggregate(tenantId, id);
  }

  @Override
  public Boolean existsByName(String name) {
    UUID tenantId = tenantContext.getTenantId();
    return dao.existsByName(tenantId, name);
  }

  @Override
  public void addAgenda(UUID dayId, Agenda agenda) {
    UUID tenantId = tenantContext.getTenantId();
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
    UUID tenantId = tenantContext.getTenantId();
    dao.removeAgenda(tenantId, dayId, agenda.getStartDate(), agenda.getEndDate());
  }

  @Override
  @Transactional
  public void updateAgenda(UUID dayId, Agenda agenda) {
    UUID tenantId = tenantContext.getTenantId();
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
  public void updateDayDetails(UUID dayId, String location, String description) {
    UUID tenantId = tenantContext.getTenantId();
    dao.updateDayDetails(tenantId, dayId, location, description);
  }
}
