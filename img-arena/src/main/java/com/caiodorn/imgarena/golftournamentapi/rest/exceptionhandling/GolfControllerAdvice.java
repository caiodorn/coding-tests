package com.caiodorn.imgarena.golftournamentapi.rest.exceptionhandling;

import com.caiodorn.imgarena.golftournamentapi.service.exception.TournamentAlreadyRecordedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice
public class GolfControllerAdvice implements ProblemHandling {

    @ExceptionHandler
    public ResponseEntity<Problem> handleTournamentAlreadyRecordedException(TournamentAlreadyRecordedException e, NativeWebRequest request) {
        return this.create(Status.CONFLICT, e, request);
    }

}
