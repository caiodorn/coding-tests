package com.caiodorn.imgarena.golftournamentapi.repository;

import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GolfTournamentRepository extends CrudRepository<GolfTournamentEntity, Long> {
    boolean existsByExternalIdAndSourceId(String externalId, SourceId sourceId);
    Optional<GolfTournamentEntity> findByExternalIdAndSourceId(String externalId, SourceId sourceId);
}
