package com.caiodorn.imgarena.golftournamentapi.service;

import com.caiodorn.imgarena.golftournamentapi.repository.GolfTournamentRepository;
import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import com.caiodorn.imgarena.golftournamentapi.service.mapper.GolfTournamentEntityMapperStrategy;
import com.caiodorn.imgarena.golftournamentapi.service.validation.GolfTournamentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GolfTournamentService {

    private final GolfTournamentEntityMapperStrategy mapperStrategy;
    private final GolfTournamentRepository repository;
    private final GolfTournamentValidator validator;

    public void create(final GolfTournamentResource resource, final SourceId sourceId) {
        final GolfTournamentEntity entity = mapperStrategy.getMappingFunction(sourceId).apply(resource);
        entity.setSourceId(sourceId);
        validator.validate(entity);
        repository.save(entity);
        log.info(String.format("Golf tournament successfully recorded in the DB -> %s", entity));
    }

}
