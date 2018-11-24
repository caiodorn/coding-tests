package com.caiodorn.codingtests.gamesys.user.business;

public class UserNameAlreadyInUseException extends RuntimeException {

    public UserNameAlreadyInUseException(String userName) {
        super(String.format("Provided userName[%s] is already in use", userName));
    }

}
