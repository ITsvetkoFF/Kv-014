package edu.softserve.zoo.persistence.exception;

import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * @author Vadym Holub
 */
public enum PersistenceReason implements ExceptionReason {
    HIBERNATE_QUERY_FAILED("Internal server error"),
    SPECIFICATION_IS_NULL("Internal server error"),
    NULL_VALUE_IN_SPECIFICATION("passed not valid request parameter");

    private final String message;

    PersistenceReason(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
