package edu.softserve.zoo.exception;

import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * @author Serhii Alekseichenko
 */
public enum WebReason implements ExceptionReason {
    MAPPING_STRATEGY_NOT_FOUND("Service unavailable"),
    MAPPING_TO_DTO_FAILED("Service unavailable"),
    MAPPING_TO_ENTITY_FAILED("Service unavailable");

    private final String message;

    WebReason(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
