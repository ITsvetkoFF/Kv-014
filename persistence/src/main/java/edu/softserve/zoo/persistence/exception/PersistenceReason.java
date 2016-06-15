package edu.softserve.zoo.persistence.exception;

import edu.softserve.zoo.core.exceptions.ExceptionReason;

/**
 * @author Vadym Holub
 */
public enum PersistenceReason implements ExceptionReason {
    HIBERNATE_QUERY_FAILED("reason.persistence.hibernate_query_failed"),
    SPECIFICATION_IS_NULL("reason.persistence.specification_is_null"),
    NULL_VALUE_IN_SPECIFICATION("reason.persistence.null_value_in_specification");

    private final String message;

    PersistenceReason(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
