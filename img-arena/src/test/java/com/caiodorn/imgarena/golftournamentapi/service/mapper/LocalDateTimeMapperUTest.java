package com.caiodorn.imgarena.golftournamentapi.service.mapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocalDateTimeMapperUTest {

    private final LocalDateTimeMapper mapper = new LocalDateTimeMapper();

    @Test
    void shouldReturnNull_whenFromLocalDate_givenNullSource() {
        assertNull(mapper.fromLocalDate(null));
    }

    @Test
    void shouldReturnExpectedObject_whenFromLocalDate_givenValidSource() {
        LocalDateTime expected = LocalDateTime.of(2000, 1,1,0,0,0);
        LocalDateTime actual = mapper.fromLocalDate(LocalDate.of(expected.getYear(),expected.getMonthValue(),expected.getDayOfMonth()));
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnNull_whenFromSeconds_givenNullSource() {
        assertNull(mapper.fromSeconds(null));
    }

    @Test
    void shouldReturnExpectedObject_whenFromSeconds_givenValidSource() {
        LocalDateTime expected = LocalDateTime.of(2000, 1,1,0,0,0);
        LocalDateTime actual = mapper.fromLocalDate(LocalDate.of(expected.getYear(),expected.getMonthValue(),expected.getDayOfMonth()));
        assertEquals(expected, actual);
    }

}
