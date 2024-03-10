package com.caiodorn.imgarena.golftournamentapi.service.mapper;

import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeAResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeBResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class GolfTournamentEntityMapperStrategy {

    private final Map<SourceId, Function<GolfTournamentResource, GolfTournamentEntity>> functionMap;

    public GolfTournamentEntityMapperStrategy(GolfTournamentEntityMapper mapper) {
        this.functionMap = Map.ofEntries(
                Map.entry(SourceId.A, source -> mapper.toEntity((GolfTournamentTypeAResource) source)),
                Map.entry(SourceId.B, source -> mapper.toEntity((GolfTournamentTypeBResource) source))
        );
    }

    public Function<GolfTournamentResource, GolfTournamentEntity> getMappingFunction(SourceId sourceId) {
        return this.functionMap.get(sourceId);
    }

}
