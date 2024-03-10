package com.caiodorn.imgarena.golftournamentapi.rest.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class GolfTournamentTypeAResource implements GolfTournamentResource {
    @NotBlank
    String tournamentId;

    @NotBlank
    String tournamentName;

    @NotBlank
    String forecast;

    @NotBlank
    String courseName;

    @NotBlank
    String countryCode;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yy")
    @JsonFormat(pattern = "dd/MM/yy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yy")
    @JsonFormat(pattern = "dd/MM/yy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate endDate;

    @NotNull
    Integer roundCount;
}
