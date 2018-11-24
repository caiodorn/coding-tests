package com.caiodorn.codingtests.gamesys.user.rest;

import com.caiodorn.codingtests.gamesys.user.business.UserNameAlreadyInUseException;
import com.caiodorn.codingtests.gamesys.user.business.BlackListedUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class UserControllerAdvice implements ProblemHandling {

    @ExceptionHandler
    public ResponseEntity<Problem> handleUserBlackListedException(BlackListedUserException e, NativeWebRequest request) {
        return this.create(Status.BAD_REQUEST, e, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUserAlreadyRegisteredException(UserNameAlreadyInUseException e, NativeWebRequest request) {
        return this.create(Status.CONFLICT, e, request);
    }

}
