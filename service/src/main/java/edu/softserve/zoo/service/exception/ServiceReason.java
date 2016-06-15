package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.core.exceptions.ExceptionReason;

/**
 * @author Vadym Holub
 */
public enum ServiceReason implements ExceptionReason {
    NOT_FOUND("reason.service.not_found"),
    INVALID_DATA_PROVIDED("reason.service.invalid_data_provided"),
    ARGUMENT_IS_NULL("reason.service.argument_is_null");

    private final String message;

    ServiceReason(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
