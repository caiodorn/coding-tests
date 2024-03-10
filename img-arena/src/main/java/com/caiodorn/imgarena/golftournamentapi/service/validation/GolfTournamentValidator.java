package com.caiodorn.imgarena.golftournamentapi.service.validation;

import com.caiodorn.imgarena.golftournamentapi.repository.GolfTournamentRepository;
import com.caiodorn.imgarena.golftournamentapi.repository.entity.GolfTournamentEntity;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import com.caiodorn.imgarena.golftournamentapi.service.exception.TournamentAlreadyRecordedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GolfTournamentValidator {

    private static final Map<SourceId, String> MSG_MAP = Map.ofEntries(
            Map.entry(SourceId.A, "A golf tournament with same tournamentId and sourceId has already been received. tournamentId=%s, sourceId=%s"),
            Map.entry(SourceId.B, "A golf tournament with same tournamentUUID and sourceId has already been received. tournamentUUID=%s, sourceId=%s")
    );

    private final GolfTournamentRepository repository;

    public void validate(GolfTournamentEntity entity) {
        validateAlreadyExists(entity);
    }

    private void validateAlreadyExists(GolfTournamentEntity entity) {
        if (repository.existsByExternalIdAndSourceId(entity.getExternalId(), entity.getSourceId())) {
            var msg = String.format(MSG_MAP.get(entity.getSourceId()), entity.getExternalId(), entity.getSourceId());
            log.error(msg);
            throw new TournamentAlreadyRecordedException(msg);
        }
    }

}
