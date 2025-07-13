package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.LocalEntity;
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

public class Day extends LocalEntity {
  private String location;
  private final OffsetDateTime date;
  private final List<Agenda> agendas;

  public Day(String location, OffsetDateTime date, List<Agenda> agendas) {
    super();
    this.date = validateDate(date);
    this.agendas = new ArrayList<>(validateAgendas(agendas));
    this.location = location;
  }

  public Day(String location, OffsetDateTime date) {
    this(location, date, new ArrayList<>());
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

  public void setLocation(String location) {
    this.location = location;
  }

  public OffsetDateTime getDate() {
    return date;
  }

  public List<Agenda> getAgendas() {
    return Collections.unmodifiableList(agendas);
  }

  public static boolean allOnDifferentDates(List<Day> days) {
    if (days == null || days.isEmpty()) {
      return false;
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
