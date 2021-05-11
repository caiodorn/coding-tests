package com.caiodorn.imgarena.golftournamentapi.service.mapper;

import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeAResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeBResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = { GolfTournamentModelMapperImpl.class, LocalDateTimeMapper.class })
public class GolfTournamentModelMapperITest {

    @Autowired
    GolfTournamentModelMapper mapper;

    @Test
    public void shouldMapAllFields_whenToEntity_givenTypeA() {
        LocalDateTime startDate = LocalDateTime.of(2000, 1,1,0,0,0);
        LocalDateTime endDate = LocalDateTime.of(2001, 1,1,0,0,0);

        GolfTournamentTypeAResource source = GolfTournamentTypeAResource.builder()
                .tournamentId("174638")
                .tournamentName("Women's Open Championship")
                .forecast("fair")
                .courseName("Sunnydale Golf Course")
                .countryCode("GB")
                .startDate(startDate.toLocalDate())
                .endDate(endDate.toLocalDate())
                .roundCount(4)
                .build();

        GolfTournamentEntity expected = GolfTournamentEntity.builder()
                .externalId("174638")
                .country("GB")
                .courseName("Sunnydale Golf Course")
                .forecast("fair")
                .tournamentName("Women's Open Championship")
                .playerCount(null)
                .numberOfRounds(4)
                .startDateTime(startDate)
                .endDateTime(endDate)
                .build();

        assertEquals(expected, mapper.toEntity(source));
    }

    @Test
    public void shouldMapAllFields_whenToEntity_givenTypeB() {
        LocalDateTime startDate = LocalDateTime.of(2000, 1,1,0,0,0);
        LocalDateTime endDate = LocalDateTime.of(2001, 1,1,0,0,0);

        GolfTournamentTypeBResource source = GolfTournamentTypeBResource.builder()
                .tournamentUUID("southWestInvitational")
                .golfCourse("Happy Days Golf Club")
                .competitionName("South West Invitational")
                .hostCountry("United States Of America")
                .epochStart(startDate.toEpochSecond(ZoneOffset.UTC))
                .epochFinish(endDate.toEpochSecond(ZoneOffset.UTC))
                .rounds(2)
                .playerCount(35)
                .build();

        GolfTournamentEntity expected = GolfTournamentEntity.builder()
                .externalId("southWestInvitational")
                .country("United States Of America")
                .courseName("Happy Days Golf Club")
                .tournamentName("South West Invitational")
                .playerCount(35)
                .numberOfRounds(2)
                .startDateTime(startDate)
                .endDateTime(endDate)
                .build();

        assertEquals(expected, mapper.toEntity(source));
    }

}
