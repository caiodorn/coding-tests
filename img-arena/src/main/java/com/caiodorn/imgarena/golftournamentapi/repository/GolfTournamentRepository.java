package com.caiodorn.imgarena.golftournamentapi.repository;

import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import org.springframework.data.repository.CrudRepository;

public interface GolfTournamentRepository extends CrudRepository<GolfTournamentEntity, Long> {
}
