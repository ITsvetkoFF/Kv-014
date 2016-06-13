package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * @author Vadym Holub
 */
public enum ServiceReason implements ExceptionReason {
    NOT_FOUND("reason.service.not_found");

    private final String message;

    ServiceReason(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
