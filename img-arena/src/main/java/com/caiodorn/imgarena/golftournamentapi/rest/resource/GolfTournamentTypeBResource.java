package com.caiodorn.imgarena.golftournamentapi.rest.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor
public class GolfTournamentTypeBResource {
    @NotBlank
    String tournamentUUID;

    @NotBlank
    String golfCourse;

    @NotBlank
    String competitionName;

    @NotBlank
    String hostCountry;

    @NotNull
    Long epochStart;

    @NotNull
    Long epochFinish;

    @NotNull
    Integer rounds;

    @NotNull
    Integer playerCount;
}
