package com.caiodorn.imgarena.golftournamentapi.repository;

import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class GolfTournamentRepositoryITest {

    @Autowired
    private GolfTournamentRepository repository;

    @Test
    void shouldThrowException_whenSave_givenUniqueConstraintViolated() {
        GolfTournamentEntity entity = GolfTournamentEntity.builder()
                .tournamentName("South West Invitational")
                .externalId("southWestInvitational")
                .country("United States Of America")
                .courseName("Happy Days Golf Club")
                .startDateTime(LocalDateTime.MIN)
                .endDateTime(LocalDateTime.MIN)
                .numberOfRounds(2)
                .sourceId(SourceId.B)
                .build();

        repository.save(entity);

        GolfTournamentEntity conflictingEntity = GolfTournamentEntity.builder()
                .tournamentName("South West Invitational")
                .externalId("southWestInvitational")
                .country("United States Of America")
                .courseName("Happy Days Golf Club")
                .startDateTime(LocalDateTime.MIN)
                .endDateTime(LocalDateTime.MIN)
                .numberOfRounds(2)
                .sourceId(SourceId.B)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> repository.save(conflictingEntity));
    }

}
