package com.caiodorn.imgarena.golftournamentapi.service.validation;

import com.caiodorn.imgarena.golftournamentapi.repository.GolfTournamentRepository;
import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import com.caiodorn.imgarena.golftournamentapi.service.exception.TournamentAlreadyRecordedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GolfTournamentValidatorUTest {

    @InjectMocks
    private GolfTournamentValidator validator;

    @Mock
    private GolfTournamentRepository repositoryMock;

    @Test
    void shouldThrowException_whenValidate_givenExistsInTheDB_SourceIdA() {
        when(repositoryMock.existsByExternalIdAndSourceId(any(), any())).thenReturn(true);
        GolfTournamentEntity entity = GolfTournamentEntity.builder().externalId("PGA Tour 2021").sourceId(SourceId.A).build();

        try {
            validator.validate(entity);
            fail("Should have thrown exception");
        } catch (TournamentAlreadyRecordedException e) {
            assertEquals("A golf tournament with same tournamentId and sourceId has already been received. tournamentId=PGA Tour 2021, sourceId=A", e.getMessage());
        }
    }

    @Test
    void shouldThrowException_whenValidate_givenExistsInTheDB_SourceIdB() {
        when(repositoryMock.existsByExternalIdAndSourceId(any(), any())).thenReturn(true);
        GolfTournamentEntity entity = GolfTournamentEntity.builder().externalId("PGA Tour 2021").sourceId(SourceId.B).build();

        try {
            validator.validate(entity);
            fail("Should have thrown exception");
        } catch (TournamentAlreadyRecordedException e) {
            assertEquals("A golf tournament with same tournamentUUID and sourceId has already been received. tournamentUUID=PGA Tour 2021, sourceId=B", e.getMessage());
        }
    }

}
