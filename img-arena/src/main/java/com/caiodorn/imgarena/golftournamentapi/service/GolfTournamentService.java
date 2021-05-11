package com.caiodorn.imgarena.golftournamentapi.service;

import com.caiodorn.imgarena.golftournamentapi.repository.GolfTournamentRepository;
import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.service.mapper.ModelMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GolfTournamentService {

    private final ModelMapperFactory<Object> mapperFactory;
    private final GolfTournamentRepository repository;

    public void create(final Object source, final String sourceId) {
        final GolfTournamentEntity entity = mapperFactory.getMappingFunction(sourceId).apply(source);
        entity.setSourceId(sourceId);

        repository.save(entity);
    }

}
