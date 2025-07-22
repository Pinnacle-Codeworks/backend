package com.markguiang.backend.event.domain.models;

import com.markguiang.backend.base.exceptions.InvalidDateRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {

  // Test data constants
  private static final OffsetDateTime START_TIME = OffsetDateTime.of(2024, 1, 15, 9, 0, 0, 0, ZoneOffset.UTC);
  private static final OffsetDateTime END_TIME = OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC);
  private static final OffsetDateTime LATER_TIME = OffsetDateTime.of(2024, 1, 15, 11, 0, 0, 0, ZoneOffset.UTC);
  private static final String LOCATION = "Conference Room A";

  @Nested
  @DisplayName("Constructor Tests")
  class ConstructorTests {

    @Test
    @DisplayName("Should create agenda with valid parameters")
    void shouldCreateAgendaWithValidParameters() {
      // Given & When
      Agenda agenda = new Agenda(START_TIME, END_TIME, LOCATION);

      // Then
      assertEquals(START_TIME, agenda.getStartDate());
      assertEquals(END_TIME, agenda.getEndDate());
      assertEquals(LOCATION, agenda.getLocation());
    }

    @Test
    @DisplayName("Should create agenda from copy constructor")
    void shouldCreateAgendaFromCopyConstructor() {
      // Given
      Agenda original = new Agenda(START_TIME, END_TIME, LOCATION);

      // When
      Agenda copy = new Agenda(original);

      // Then
      assertEquals(original.getStartDate(), copy.getStartDate());
      assertEquals(original.getEndDate(), copy.getEndDate());
      assertEquals(original.getLocation(), copy.getLocation());

      // Verify it's a deep copy (different object references)
      assertNotSame(original, copy);
    }

    @Test
    @DisplayName("Should allow start and end dates to be equal")
    void shouldAllowStartAndEndDatesToBeEqual() {
      // Given & When & Then
      assertDoesNotThrow(() -> new Agenda(START_TIME, START_TIME, LOCATION));
    }

    @Test
    @DisplayName("Should throw exception when start date is null")
    void shouldThrowExceptionWhenStartDateIsNull() {
      // Given & When & Then
      assertThrows(NullPointerException.class,
          () -> new Agenda(null, END_TIME, LOCATION));
    }

    @Test
    @DisplayName("Should throw exception when end date is null")
    void shouldThrowExceptionWhenEndDateIsNull() {
      // Given & When & Then
      assertThrows(NullPointerException.class,
          () -> new Agenda(START_TIME, null, LOCATION));
    }

    @Test
    @DisplayName("Should throw exception when start date is after end date")
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
      // Given
      OffsetDateTime laterStart = END_TIME.plusHours(1);

      // When & Then
      assertThrows(
          InvalidDateRangeException.class,
          () -> new Agenda(laterStart, END_TIME, LOCATION));
    }

    @Test
    @DisplayName("Should allow null location")
    void shouldAllowNullLocation() {
      // Given & When & Then
      assertDoesNotThrow(() -> new Agenda(START_TIME, END_TIME, null));
    }
  }

  @Nested
  @DisplayName("hasOverlappingTimes Tests")
  class HasOverlappingTimesTests {

    @Test
    @DisplayName("Should return false for empty list")
    void shouldReturnFalseForEmptyList() {
      // Given
      List<Agenda> emptyList = Collections.emptyList();

      // When
      boolean result = Agenda.hasOverlappingTimes(emptyList);

      // Then
      assertFalse(result);
    }

    @Test
    @DisplayName("Should return false for single agenda")
    void shouldReturnFalseForSingleAgenda() {
      // Given
      List<Agenda> singleAgenda = List.of(
          new Agenda(START_TIME, END_TIME, LOCATION));

      // When
      boolean result = Agenda.hasOverlappingTimes(singleAgenda);

      // Then
      assertFalse(result);
    }

    @Test
    @DisplayName("Should return false for non-overlapping agendas")
    void shouldReturnFalseForNonOverlappingAgendas() {
      // Given
      List<Agenda> nonOverlappingAgendas = Arrays.asList(
          new Agenda(START_TIME, END_TIME, LOCATION),
          new Agenda(END_TIME, LATER_TIME, LOCATION) // Starts exactly when first ends
      );

      // When
      boolean result = Agenda.hasOverlappingTimes(nonOverlappingAgendas);

      // Then
      assertFalse(result);
    }

    @Test
    @DisplayName("Should return true for overlapping agendas")
    void shouldReturnTrueForOverlappingAgendas() {
      // Given
      OffsetDateTime overlappingStart = START_TIME.plusMinutes(30);
      List<Agenda> overlappingAgendas = Arrays.asList(
          new Agenda(START_TIME, END_TIME, LOCATION),
          new Agenda(overlappingStart, LATER_TIME, LOCATION));

      // When
      boolean result = Agenda.hasOverlappingTimes(overlappingAgendas);

      // Then
      assertTrue(result);
    }

    @Test
    @DisplayName("Should handle unsorted input correctly")
    void shouldHandleUnsortedInputCorrectly() {
      // Given - agendas in reverse chronological order
      OffsetDateTime overlappingStart = START_TIME.plusMinutes(30);
      List<Agenda> unsortedAgendas = Arrays.asList(
          new Agenda(overlappingStart, LATER_TIME, LOCATION), // Later agenda first
          new Agenda(START_TIME, END_TIME, LOCATION) // Earlier agenda second
      );

      // When
      boolean result = Agenda.hasOverlappingTimes(unsortedAgendas);

      // Then
      assertTrue(result);
    }

    @Test
    @DisplayName("Should return true for multiple overlapping agendas")
    void shouldReturnTrueForMultipleOverlappingAgendas() {
      // Given
      OffsetDateTime time1 = OffsetDateTime.of(2024, 1, 15, 9, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime time2 = OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime time3 = OffsetDateTime.of(2024, 1, 15, 9, 30, 0, 0, ZoneOffset.UTC);
      OffsetDateTime time4 = OffsetDateTime.of(2024, 1, 15, 11, 0, 0, 0, ZoneOffset.UTC);

      List<Agenda> multipleOverlapping = Arrays.asList(
          new Agenda(time1, time2, LOCATION), // 9:00 - 10:00
          new Agenda(time3, time4, LOCATION), // 9:30 - 11:00 (overlaps with first)
          new Agenda(time2, time4, LOCATION) // 10:00 - 11:00
      );

      // When
      boolean result = Agenda.hasOverlappingTimes(multipleOverlapping);

      // Then
      assertTrue(result);
    }

    @Test
    @DisplayName("Should not modify original list")
    void shouldNotModifyOriginalList() {
      // Given
      List<Agenda> originalList = new ArrayList<>(Arrays.asList(
          new Agenda(LATER_TIME, LATER_TIME.plusHours(1), LOCATION),
          new Agenda(START_TIME, END_TIME, LOCATION)));
      List<Agenda> originalOrder = new ArrayList<>(originalList);

      // When
      Agenda.hasOverlappingTimes(originalList);

      // Then
      assertEquals(originalOrder.get(0).getStartDate(), originalList.get(0).getStartDate());
      assertEquals(originalOrder.get(1).getStartDate(), originalList.get(1).getStartDate());
    }
  }

  @Nested
  @DisplayName("allOnDate Tests")
  class AllOnDateTests {

    @Test
    @DisplayName("Should return true for empty list")
    void shouldReturnTrueForEmptyList() {
      // Given
      List<Agenda> emptyList = Collections.emptyList();

      // When
      boolean result = Agenda.allOnDate(emptyList, START_TIME);

      // Then
      assertTrue(result);
    }

    @Test
    @DisplayName("Should return true when all agendas are on the same date")
    void shouldReturnTrueWhenAllAgendasAreOnSameDate() {
      // Given
      OffsetDateTime targetDate = OffsetDateTime.of(2024, 1, 15, 0, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime morning = OffsetDateTime.of(2024, 1, 15, 9, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime afternoon = OffsetDateTime.of(2024, 1, 15, 15, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime evening = OffsetDateTime.of(2024, 1, 15, 18, 0, 0, 0, ZoneOffset.UTC);

      List<Agenda> sameDayAgendas = Arrays.asList(
          new Agenda(morning, afternoon, LOCATION),
          new Agenda(afternoon, evening, "Room B"));

      // When
      boolean result = Agenda.allOnDate(sameDayAgendas, targetDate);

      // Then
      assertTrue(result);
    }

    @Test
    @DisplayName("Should return false when agenda start date is on different date")
    void shouldReturnFalseWhenAgendaStartDateIsOnDifferentDate() {
      // Given
      OffsetDateTime targetDate = OffsetDateTime.of(2024, 1, 15, 0, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime differentDay = OffsetDateTime.of(2024, 1, 16, 9, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime sameDay = OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC);

      List<Agenda> mixedDayAgendas = List.of(
          new Agenda(sameDay, differentDay, LOCATION));

      // When
      boolean result = Agenda.allOnDate(mixedDayAgendas, targetDate);

      // Then
      assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when agenda end date is on different date")
    void shouldReturnFalseWhenAgendaEndDateIsOnDifferentDate() {
      // Given
      OffsetDateTime targetDate = OffsetDateTime.of(2024, 1, 15, 0, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime sameDay = OffsetDateTime.of(2024, 1, 15, 9, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime differentDay = OffsetDateTime.of(2024, 1, 16, 10, 0, 0, 0, ZoneOffset.UTC);

      List<Agenda> mixedDayAgendas = List.of(
          new Agenda(sameDay, differentDay, LOCATION));

      // When
      boolean result = Agenda.allOnDate(mixedDayAgendas, targetDate);

      // Then
      assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when multiple agendas exist but first one spans different dates")
    void shouldReturnFalseWhenMultipleAgendasExistButFirstOneSpansDifferentDates() {
      // Given
      OffsetDateTime targetDate = OffsetDateTime.of(2024, 1, 15, 0, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime differentDay = OffsetDateTime.of(2024, 1, 16, 9, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime sameDay = OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC);

      List<Agenda> agendas = Arrays.asList(
          new Agenda(sameDay, differentDay, LOCATION),
          new Agenda(sameDay, sameDay.plusHours(1), "Room B"));

      // When
      boolean result = Agenda.allOnDate(agendas, targetDate);

      // Then
      assertFalse(result);
    }
  }

  @Nested
  @DisplayName("Parameterized Tests")
  class ParameterizedTests {

    @ParameterizedTest
    @MethodSource("provideOverlappingScenarios")
    @DisplayName("Should correctly identify overlapping scenarios")
    void shouldCorrectlyIdentifyOverlappingScenarios(List<Agenda> agendas, boolean expectedOverlap,
        String description) {
      // When
      boolean result = Agenda.hasOverlappingTimes(agendas);

      // Then
      assertEquals(expectedOverlap, result, description);
    }

    static Stream<Arguments> provideOverlappingScenarios() {
      OffsetDateTime time1 = OffsetDateTime.of(2024, 1, 15, 9, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime time2 = OffsetDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime time3 = OffsetDateTime.of(2024, 1, 15, 11, 0, 0, 0, ZoneOffset.UTC);
      OffsetDateTime time4 = OffsetDateTime.of(2024, 1, 15, 12, 0, 0, 0, ZoneOffset.UTC);

      return Stream.of(
          Arguments.of(
              Arrays.asList(
                  new Agenda(time1, time2, LOCATION),
                  new Agenda(time2, time3, LOCATION)),
              false,
              "Adjacent agendas should not overlap"),
          Arguments.of(
              Arrays.asList(
                  new Agenda(time1, time3, LOCATION),
                  new Agenda(time2, time4, LOCATION)),
              true,
              "Overlapping agendas should be detected"),
          Arguments.of(
              Arrays.asList(
                  new Agenda(time1, time2, LOCATION),
                  new Agenda(time3, time4, LOCATION)),
              false,
              "Separate agendas should not overlap"),
          Arguments.of(
              Arrays.asList(
                  new Agenda(time1, time4, LOCATION),
                  new Agenda(time2, time3, LOCATION)),
              true,
              "Contained agenda should be detected as overlap"));
    }
  }
}
