package com.caiodorn.imgarena.golftournamentapi.service.mapper;

import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeAResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeBResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class ModelMapperFactory<S> {

    private final Map<String, Function<S, GolfTournamentEntity>> functionMap;

    public ModelMapperFactory(GolfTournamentModelMapper mapper) {
        Map<String, Function<S, GolfTournamentEntity>> functionMap = new HashMap<>();
        functionMap.put("A", source -> mapper.toEntity((GolfTournamentTypeAResource) source));
        functionMap.put("B", source -> mapper.toEntity((GolfTournamentTypeBResource) source));
        this.functionMap = Map.copyOf(functionMap);
    }

    public Function<S, GolfTournamentEntity> getMappingFunction(String sourceId) {
        return functionMap.get(sourceId);
    }

}
