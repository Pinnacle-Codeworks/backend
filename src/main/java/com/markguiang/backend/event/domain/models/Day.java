package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.model.LocalEntity;
import com.markguiang.backend.event.exceptions.AgendaNotFoundException;
import com.markguiang.backend.event.exceptions.AgendasOnDifferentDateException;
import com.markguiang.backend.event.exceptions.DateHasTimeException;
import com.markguiang.backend.event.exceptions.OverlappingAgendaTimeException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Day extends LocalEntity {
  private String description;
  private String location;
  private final OffsetDateTime date;
  private final List<Agenda> agendas;

  public Day(
      UUID ID, String location, OffsetDateTime date, List<Agenda> agendas, String description) {
    super(ID);
    this.date = validateDate(date);
    this.agendas = new ArrayList<>(validateAgendas(agendas));
    this.location = location;
    this.description = description;
  }

  public Day(String location, OffsetDateTime date, List<Agenda> agendas, String description) {
    this(null, location, date, agendas, description);
  }

  public void addAgenda(Agenda agenda) {
    Objects.requireNonNull(agenda);

    List<Agenda> tempList = new ArrayList<>(agendas);
    tempList.add(agenda);
    validateAgendas(tempList);

    agendas.add(agenda);
    sortAgendas();
  }

  public void removeAgenda(Agenda agenda) {
    Objects.requireNonNull(agenda);
    if (!agendas.remove(agenda)) {
      throw new AgendaNotFoundException(agenda.getStartDate());
    }
  }

  public String getLocation() {
    return location;
  }

  public OffsetDateTime getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

  public List<Agenda> getAgendas() {
    return Collections.unmodifiableList(agendas);
  }

  public void updateData(String location, String description) {
    this.location = location;
    this.description = description;
  }

  public static boolean allOnDifferentDates(List<Day> days) {
    if (days == null || days.isEmpty()) {
      return true;
    }
    Set<LocalDate> uniqueDays = new HashSet<>();
    for (Day day : days) {
      if (!uniqueDays.add(day.getDate().toLocalDate())) {
        return false;
      }
    }
    return true;
  }

  private OffsetDateTime validateDate(OffsetDateTime date) {
    requireNonNull(date, "date");
    OffsetDateTime dateOnly = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
    if (!date.equals(dateOnly)) {
      throw new DateHasTimeException();
    }
    return date;
  }

  private List<Agenda> validateAgendas(List<Agenda> agendas) {
    requireNonNull(agendas, "agendas");
    for (Agenda agenda : agendas) {
      Objects.requireNonNull(agenda);
    }
    if (!Agenda.allOnDate(agendas, date)) {
      throw new AgendasOnDifferentDateException();
    }
    if (Agenda.hasOverlappingTimes(agendas)) {
      throw new OverlappingAgendaTimeException();
    }
    return agendas;
  }

  private void sortAgendas() {
    agendas.sort(Comparator.comparing(Agenda::getStartDate));
  }
}