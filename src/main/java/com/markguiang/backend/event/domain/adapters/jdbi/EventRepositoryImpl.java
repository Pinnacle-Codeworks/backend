package com.markguiang.backend.event.domain.adapters.jdbi;

import com.fasterxml.uuid.Generators;
import com.markguiang.backend.event.domain.adapters.jdbi.mappers.AgendaInsertDto;
import com.markguiang.backend.event.domain.models.Agenda;
import com.markguiang.backend.event.domain.models.Day;
import com.markguiang.backend.event.domain.models.Event;
import com.markguiang.backend.event.domain.ports.EventRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EventRepositoryImpl implements EventRepository {

  private final EventDao dao;

  public EventRepositoryImpl(EventDao dao) {
    this.dao = dao;
  }

  @Override
  @Transactional
  public UUID save(Event event) {
    dao.insertEvent(event);

    for (Day day : event.getDays()) {
      dao.insertDay(
          day.getId(), event.getId(), day.getLocation(), day.getDate(), day.getDescription());

      List<AgendaInsertDto> agendaDTOs = day.getAgendas().stream()
          .map(
              agenda -> new AgendaInsertDto(
                  Generators.timeBasedEpochGenerator().generate(),
                  day.getId(),
                  agenda.getStartDate(),
                  agenda.getEndDate(),
                  agenda.getLocation()))
          .toList();

      dao.insertAgendas(agendaDTOs);
    }

    return event.getId();
  }

  @Override
  public Optional<Event> findByID(UUID id) {
    return dao.findEventAggregate(id);
  }

  @Override
  public Boolean existsByName(String name) {
    return dao.existsByName(name);
  }

  @Override
  public void addAgenda(UUID dayId, Agenda agenda) {
    AgendaInsertDto agendaDTO = new AgendaInsertDto(
        Generators.timeBasedEpochGenerator().generate(),
        dayId,
        agenda.getStartDate(),
        agenda.getEndDate(),
        agenda.getLocation());
    dao.insertAgenda(agendaDTO);
  }

  @Override
  public void removeAgenda(UUID dayId, Agenda agenda) {
    dao.removeAgenda(dayId, agenda.getStartDate(), agenda.getEndDate());
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
    dao.removeAgenda(dayId, agendaDTO.getStartDate(), agendaDTO.getEndDate());
    dao.insertAgenda(agendaDTO);
  }

  @Override
  public void updateDay(Day day) {
    dao.updateDay(day.getId(), day.getLocation(), day.getDescription());
  }
}
