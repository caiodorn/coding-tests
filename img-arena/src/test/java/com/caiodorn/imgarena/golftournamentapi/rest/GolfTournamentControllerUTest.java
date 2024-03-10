package com.caiodorn.imgarena.golftournamentapi.rest;

import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeAResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeBResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import com.caiodorn.imgarena.golftournamentapi.service.GolfTournamentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GolfTournamentControllerUTest {

    @Mock
    GolfTournamentService serviceMock;

    @InjectMocks
    GolfTournamentController controller;

    @Test
    void shouldCreate_whenCreateGolfTournament_givenSourceId_A() {
        GolfTournamentTypeAResource resource = GolfTournamentTypeAResource.builder().build();

        controller.createGolfTournament(resource);

        verify(serviceMock, times(1)).create(resource, SourceId.A);
    }

    @Test
    void shouldCreate_whenCreateGolfTournament_givenSourceId_B() {
        GolfTournamentTypeBResource resource = GolfTournamentTypeBResource.builder().build();

        controller.createGolfTournament(resource);

        verify(serviceMock, times(1)).create(resource, SourceId.B);
    }

}
