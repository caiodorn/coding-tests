package com.caiodorn.imgarena.golftournamentapi.service;

import com.caiodorn.imgarena.golftournamentapi.repository.GolfTournamentRepository;
import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeAResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import com.caiodorn.imgarena.golftournamentapi.service.exception.TournamentAlreadyRecordedException;
import com.caiodorn.imgarena.golftournamentapi.service.mapper.GolfTournamentEntityMapperStrategy;
import com.caiodorn.imgarena.golftournamentapi.service.validation.GolfTournamentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GolfTournamentServiceUTest {

    @InjectMocks
    private GolfTournamentService golfTournamentService;

    @Mock
    private GolfTournamentEntityMapperStrategy mapperStrategyMock;

    @Mock
    private GolfTournamentRepository repositoryMock;

    @Mock
    private GolfTournamentValidator validatorMock;

    @Mock
    private Function<GolfTournamentResource, GolfTournamentEntity> functionMock;

    @BeforeEach
    void setup() {
        given(mapperStrategyMock.getMappingFunction(any(SourceId.class))).willReturn(functionMock);
        given(functionMock.apply(any(GolfTournamentResource.class))).willReturn(GolfTournamentEntity.builder().build());
    }

    @Test
    void shouldPersistEntity_whenCreate() {
        GolfTournamentResource resource = GolfTournamentTypeAResource.builder().build();

        golfTournamentService.create(resource, SourceId.A);

        verify(repositoryMock, times(1)).save(any(GolfTournamentEntity.class));
    }

    @Test
    void shouldNotCallSave_whenCreate_givenValidationFails() {
        doThrow(TournamentAlreadyRecordedException.class).when(validatorMock).validate(any(GolfTournamentEntity.class));

        try {
            GolfTournamentResource resource = GolfTournamentTypeAResource.builder().build();
            golfTournamentService.create(resource, SourceId.A);
        } catch (TournamentAlreadyRecordedException e) {
            verify(repositoryMock, never()).save(any(GolfTournamentEntity.class));
        }
    }

}


