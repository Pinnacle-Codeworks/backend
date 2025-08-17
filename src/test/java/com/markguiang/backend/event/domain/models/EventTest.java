package com.markguiang.backend.event.domain.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.markguiang.backend.event.exceptions.DayNotFoundException;
import com.markguiang.backend.event.exceptions.DaysOnSameDateException;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Event")
class EventTest {

  static final OffsetDateTime DATE = OffsetDateTime.parse("2025-07-22T00:00:00+00:00");

  static Event createEventWithDays(Day... days) {
    return Event.create(
        "Test Event",
        false,
        "desc",
        "location",
        "https://img",
        "path",
        Event.EventStatus.DRAFT,
        Arrays.asList(days));
  }

  static Day day(String loc, OffsetDateTime date, String desc) {
    return Day.createEmpty(loc, date, desc);
  }

  @Nested
  @DisplayName("addDay")
  class AddDayTests {

    @Test
    @DisplayName("given unique date when added then succeeds")
    void givenUniqueDate_whenAddDay_thenSucceeds() {
      Event event = createEventWithDays(day("loc1", DATE, "desc"));

      Day newDay = day("loc2", DATE.plusDays(1), "new");
      event.addDay(newDay);

      assertEquals(2, event.getDays().size());
      assertTrue(event.getDays().stream().anyMatch(d -> d.getDate().equals(newDay.getDate())));
    }

    @Test
    @DisplayName("given duplicate date when added then throws DaysOnSameDateException")
    void givenDuplicateDate_whenAddDay_thenThrows() {
      Day existing = day("loc", DATE, "desc");
      Event event = createEventWithDays(existing);

      Day duplicate = day("loc2", DATE, "dup");
      assertThrows(DaysOnSameDateException.class, () -> event.addDay(duplicate));
    }
  }

  @Nested
  @DisplayName("addAgendaToDay")
  class AddAgendaTests {

    @Test
    @DisplayName("given agenda on existing day when added then agenda is added")
    void givenAgendaWithMatchingDay_whenAddAgenda_thenAdded() {
      Agenda agenda = new Agenda(DATE.plusHours(1), DATE.plusHours(2), "topic");
      Day day = day("loc", DATE, "desc");

      Event event = createEventWithDays(day);
      event.addAgendaToDay(agenda);

      assertEquals(1, event.getDays().get(0).getAgendas().size());
    }

    @Test
    @DisplayName("given agenda with no matching day when added then throws DayNotFoundException")
    void givenAgendaWithNoDay_whenAddAgenda_thenThrows() {
      Event event = createEventWithDays(day("loc", DATE, "desc"));
      Agenda agenda = new Agenda(DATE.plusDays(1).plusHours(1), DATE.plusDays(1).plusHours(2), "other");

      assertThrows(DayNotFoundException.class, () -> event.addAgendaToDay(agenda));
    }
  }

  @Nested
  @DisplayName("removeAgendaFromDay")
  class RemoveAgendaTests {

    @Test
    @DisplayName("given agenda on existing day when removed then agenda is gone")
    void givenAgendaOnDay_whenRemoveAgenda_thenRemoved() {
      Agenda agenda = new Agenda(DATE.plusHours(1), DATE.plusHours(2), "topic");
      Day day = day("loc", DATE, "desc");
      day.addAgenda(agenda);

      Event event = createEventWithDays(day);
      event.removeAgendaFromDay(agenda);

      assertTrue(event.getDays().get(0).getAgendas().isEmpty());
    }

    @Test
    @DisplayName("given agenda not found on any day then throws DayNotFoundException")
    void givenAgendaWithNoDay_whenRemoveAgenda_thenThrows() {
      Event event = createEventWithDays(day("loc", DATE, "desc"));
      Agenda agenda = new Agenda(DATE.plusDays(3).plusHours(1), DATE.plusDays(3).plusHours(2), "ghost");

      assertThrows(DayNotFoundException.class, () -> event.removeAgendaFromDay(agenda));
    }
  }

  @Nested
  @DisplayName("updateDayDetails")
  class UpdateDayTests {

    @Test
    @DisplayName("given valid dayId and date when updated then fields are changed")
    void givenCorrectDay_whenUpdate_thenUpdated() {
      Day original = day("loc", DATE, "desc");
      Event event = createEventWithDays(original);
      UUID id = event.getDays().get(0).getId();

      event.updateDayDetails(id, DATE, "newLoc", "newDesc");

      Day updated = event.getDays().get(0);
      assertEquals("newLoc", updated.getLocation());
      assertEquals("newDesc", updated.getDescription());
    }

    @Test
    @DisplayName("given non-existing day when updated then throws DayNotFoundException")
    void givenWrongDay_whenUpdate_thenThrows() {
      Day day = day("loc", DATE, "desc");
      Event event = createEventWithDays(day);

      assertThrows(DayNotFoundException.class, () -> event.updateDayDetails(UUID.randomUUID(), DATE, "x", "x"));
    }
  }

  @Nested
  @DisplayName("updateDetails")
  class UpdateEventDetails {

    @Test
    @DisplayName("when updated then fields are changed")
    void whenUpdateDetails_thenUpdated() {
      Event event = createEventWithDays(day("loc", DATE, "desc"));
      event.updateDetails("newDesc", "newLoc");

      assertEquals("newDesc", event.getDescription());
      assertEquals("newLoc", event.getLocation());
    }
  }

  @Nested
  @DisplayName("updateImage")
  class UpdateImage {

    @Test
    @DisplayName("when updated then url is changed")
    void whenUpdateImage_thenUrlChanged() {
      Event event = createEventWithDays(day("loc", DATE, "desc"));
      String newUri = "test";
      event.updateImage(newUri);

      assertEquals(newUri, event.getImgURL());
    }
  }

  @Nested
  @DisplayName("validation")
  class ValidationTests {

    static Stream<List<Day>> invalidDayScenarios() {
      return Stream.of(
          Arrays.asList(day("loc", DATE, "d1"), day("loc", DATE, "d2")), // same date
          Arrays.asList(day("loc", DATE, "d1"), null) // null day
      );
    }

    @ParameterizedTest
    @MethodSource("invalidDayScenarios")
    @DisplayName("given invalid day list when creating then throws")
    void givenInvalidDays_whenCreate_thenThrows(List<Day> badDays) {
      assertThrows(RuntimeException.class,
          () -> Event.create("x", false, "x", "x", "https://x", "x", Event.EventStatus.DRAFT, badDays));
    }
  }
}
