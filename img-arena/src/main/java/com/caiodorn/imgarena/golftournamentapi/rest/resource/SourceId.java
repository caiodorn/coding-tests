package com.caiodorn.imgarena.golftournamentapi.rest.resource;

/**
 * Represents different sources where requests originate from.
 * This is required for binding incoming requests to the correct endpoint (since the only thing
 * that differentiate them is the resource -- each "source" has its own payload format).
 */
public enum SourceId {
    A,
    B
}
