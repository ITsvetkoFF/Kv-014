package edu.softserve.zoo.web.exception;

import edu.softserve.zoo.core.exceptions.ExceptionReason;

/**
 * @author Serhii Alekseichenko
 */
public enum WebReason implements ExceptionReason {
    MAPPING_STRATEGY_NOT_FOUND("reason.web.mapping_strategy_not_found"),
    MAPPING_TO_DTO_FAILED("reason.web.mapping_to_dto_failed"),
    MAPPING_TO_ENTITY_FAILED("reason.web.mapping_to_entity_failed"),
    ENUM_MAPPING_FAILED("reason.web.enum_mapping_failed");

    private final String message;

    WebReason(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
