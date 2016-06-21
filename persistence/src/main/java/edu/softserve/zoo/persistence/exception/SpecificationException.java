package edu.softserve.zoo.persistence.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.persistence.specification.Specification;

/**
 * SpecificationException reports about all kinds of problems related to {@link Specification}
 *
 * @author Vadym Holub
 */
public class SpecificationException extends ApplicationException {

    public SpecificationException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        NULL_ID_VALUE_IN_SPECIFICATION("reason.persistence.null_id_value_in_specification"),
        NULL_EMAIL("reason.persistence.null_email");

        private final String message;

        Reason(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
