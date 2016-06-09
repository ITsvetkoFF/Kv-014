package edu.softserve.zoo.exception;

import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * @author Ilya Doroshenko
 */
public enum SecurityReason implements ExceptionReason {
    UNAUTHORIZED("You don't have rights to perform this request.");

    private final String message;

    SecurityReason(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
