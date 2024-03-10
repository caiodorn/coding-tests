package com.caiodorn.imgarena.golftournamentapi.rest;

import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeAResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.GolfTournamentTypeBResource;
import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import com.caiodorn.imgarena.golftournamentapi.service.GolfTournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/golf-tournament", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
public class GolfTournamentController {

    private final GolfTournamentService service;

    @PostMapping(headers = "sourceId=A")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGolfTournament(@RequestBody @Valid GolfTournamentTypeAResource resource) {
        service.create(resource, SourceId.A);
    }

    @PostMapping(headers = "sourceId=B")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGolfTournament(@RequestBody @Valid GolfTournamentTypeBResource resource) {
        service.create(resource, SourceId.B);
    }

}
