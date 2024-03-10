package com.caiodorn.imgarena.golftournamentapi.service.mapper;

import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeAResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeBResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class GolfTournamentEntityMapperStrategyUTest {

    @Mock
    private GolfTournamentEntityMapper mapperMock;

    @InjectMocks
    private GolfTournamentEntityMapperStrategy strategy;

    @Test
    void shouldReturnAppropriateFunction_whenGetMappingFuntion_givenMatchingType() {
        try {
            strategy.getMappingFunction(SourceId.A).apply(GolfTournamentTypeAResource.builder().build());
            strategy.getMappingFunction(SourceId.B).apply(GolfTournamentTypeBResource.builder().build());
        } catch (ClassCastException e) {
            fail("Provided type does not match SourceId");
        }
    }

}
