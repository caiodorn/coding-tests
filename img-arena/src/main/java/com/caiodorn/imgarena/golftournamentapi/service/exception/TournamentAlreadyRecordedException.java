package com.caiodorn.imgarena.golftournamentapi.service.exception;

public class TournamentAlreadyRecordedException extends RuntimeException {
    public TournamentAlreadyRecordedException(String msg) {
        super(msg);
    }
}
