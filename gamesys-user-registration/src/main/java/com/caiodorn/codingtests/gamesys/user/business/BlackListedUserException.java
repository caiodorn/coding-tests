package com.caiodorn.codingtests.gamesys.user.business;

public class BlackListedUserException extends RuntimeException {

    public BlackListedUserException(String dob, String ssn) {
        super(String.format("User with DOB[%s] and SSN[%s] is currently banned and cannot be registered", dob, ssn));
    }

}
