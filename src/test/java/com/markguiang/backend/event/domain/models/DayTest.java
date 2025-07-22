package com.markguiang.backend.event.domain.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.markguiang.backend.event.exceptions.AgendaNotFoundException;
import com.markguiang.backend.event.exceptions.AgendasOnDifferentDateException;
import com.markguiang.backend.event.exceptions.DateHasTimeException;
import com.markguiang.backend.event.exceptions.OverlappingAgendaTimeException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day Aggregate Tests")
class DayTest {

  private static OffsetDateTime date(int day) {
    return OffsetDateTime.of(2025, 7, day, 0, 0, 0, 0, ZoneOffset.UTC);
  }

  private static Agenda agenda(OffsetDateTime start, OffsetDateTime end) {
    return new Agenda(start, end, "Room");
  }

  @Nested
  @DisplayName("Factory Method Tests")
  class FactoryTests {

    @Test
    @DisplayName("Should create empty day")
    void shouldCreateEmptyDay() {
      // Given
      String location = "Hall";
      OffsetDateTime dayDate = date(22);
      String description = "Prep Day";

      // When
      Day day = Day.createEmpty(location, dayDate, description);

      // Then
      assertEquals(location, day.getLocation());
      assertEquals(dayDate, day.getDate());
      assertEquals(description, day.getDescription());
      assertTrue(day.getAgendas().isEmpty());
    }

    @Test
    @DisplayName("Should load day from persistence")
    void shouldLoadDayFromPersistence() {
      // Given
      UUID id = UUID.randomUUID();
      Agenda a = agenda(date(22).withHour(8), date(22).withHour(9));
      List<Agenda> agendas = List.of(a);

      // When
      Day day = Day.loadFromPersistence(id, "Venue", date(22), "Event", agendas);

      // Then
      assertEquals(id, day.getId());
      assertEquals(1, day.getAgendas().size());
    }

    @Test
    @DisplayName("Should copy existing day")
    void shouldCopyDay() {
      // Given
      Agenda a = agenda(date(22).withHour(10), date(22).withHour(11));
      Day original = Day.createEmpty("Room", date(22), "Talks");
      original.addAgenda(a);

      // When
      Day copy = Day.copyFrom(original);

      // Then
      assertNotSame(original, copy);
      assertEquals(original.getDate(), copy.getDate());
      assertEquals(1, copy.getAgendas().size());
    }
  }

  @Nested
  @DisplayName("Agenda Modification Tests")
  class AgendaModificationTests {

    @Test
    @DisplayName("Should add agenda")
    void shouldAddAgenda() {
      // Given
      Day day = Day.createEmpty("Room", date(22), "desc");
      Agenda a = agenda(date(22).withHour(9), date(22).withHour(10));

      // When
      day.addAgenda(a);

      // Then
      assertEquals(1, day.getAgendas().size());
    }

    @Test
    @DisplayName("Should remove existing agenda")
    void shouldRemoveAgenda() {
      // Given
      Day day = Day.createEmpty("Room", date(22), "desc");
      Agenda a = agenda(date(22).withHour(9), date(22).withHour(10));
      day.addAgenda(a);

      // When
      day.removeAgenda(a);

      // Then
      assertTrue(day.getAgendas().isEmpty());
    }

    @Test
    @DisplayName("Should throw if removing non-existent agenda")
    void shouldThrowWhenRemovingNonexistentAgenda() {
      // Given
      Day day = Day.createEmpty("Room", date(22), "desc");
      Agenda a = agenda(date(22).withHour(9), date(22).withHour(10));

      // When & Then
      assertThrows(AgendaNotFoundException.class, () -> day.removeAgenda(a));
    }

    @Test
    @DisplayName("Should throw if adding agenda on different date")
    void shouldThrowWhenAgendaIsOnDifferentDate() {
      // Given
      Day day = Day.createEmpty("Room", date(22), "desc");
      Agenda a = agenda(date(23).withHour(9), date(23).withHour(10));

      // When & Then
      assertThrows(AgendasOnDifferentDateException.class, () -> day.addAgenda(a));
    }

    @Test
    @DisplayName("Should throw on overlapping agendas")
    void shouldThrowOnOverlappingAgendas() {
      // Given
      Day day = Day.createEmpty("Room", date(22), "desc");
      day.addAgenda(agenda(date(22).withHour(8), date(22).withHour(10)));
      Agenda overlapping = agenda(date(22).withHour(9), date(22).withHour(11));

      // When & Then
      assertThrows(OverlappingAgendaTimeException.class, () -> day.addAgenda(overlapping));
    }
  }

  @Nested
  @DisplayName("Update Method Tests")
  class UpdateTests {

    @Test
    @DisplayName("Should update location and description")
    void shouldUpdateDetails() {
      // Given
      Day day = Day.createEmpty("Old", date(22), "Old desc");

      // When
      day.updateDetails("New", "New desc");

      // Then
      assertEquals("New", day.getLocation());
      assertEquals("New desc", day.getDescription());
    }
  }

  @Nested
  @DisplayName("Date Validation Tests")
  class DateValidationTests {

    @Test
    @DisplayName("Should throw if date has non-zero time")
    void shouldThrowIfDateHasTimeComponent() {
      // Given
      OffsetDateTime withTime = OffsetDateTime.of(2025, 7, 22, 1, 0, 0, 0, ZoneOffset.UTC);

      // When & Then
      assertThrows(DateHasTimeException.class, () -> Day.createEmpty("X", withTime, "desc"));
    }
  }

  @Nested
  @DisplayName("Utility Method Tests")
  class UtilityTests {

    @ParameterizedTest(name = "{2}")
    @MethodSource("provideDifferentDateScenarios")
    @DisplayName("Should detect unique vs duplicate day dates")
    void shouldDetectDifferentDates(List<Day> days, boolean expected, String description) {
      // Given is parameterized

      // When
      boolean result = Day.allOnDifferentDates(days);

      // Then
      assertEquals(expected, result);
    }

    static Stream<Arguments> provideDifferentDateScenarios() {
      Day d1 = Day.createEmpty("L1", date(21), "a");
      Day d2 = Day.createEmpty("L2", date(22), "b");
      Day d3 = Day.createEmpty("L3", date(23), "c");
      Day d4 = Day.createEmpty("L4", date(22), "d");
      return Stream.of(
          Arguments.of(List.of(d1, d2, d3), true, "All days have different dates"),
          Arguments.of(List.of(d1, d2, d4), false, "Duplicate date detected"),
          Arguments.of(Collections.emptyList(), true, "Empty list returns true"),
          Arguments.of(null, true, "Null list returns true"));
    }
  }
}
