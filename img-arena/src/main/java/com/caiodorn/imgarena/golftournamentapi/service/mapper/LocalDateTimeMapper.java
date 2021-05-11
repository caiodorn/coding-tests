package com.caiodorn.imgarena.golftournamentapi.service.mapper;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Component
public class LocalDateTimeMapper {

    public LocalDateTime fromSeconds(final Long seconds) {
        return Optional.ofNullable(seconds)
                .map(s -> LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneOffset.UTC))
                .orElse(null);
    }

    public LocalDateTime fromLocalDate(final LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(s -> localDate.atTime(0, 0, 0))
                .orElse(null);
    }

}
