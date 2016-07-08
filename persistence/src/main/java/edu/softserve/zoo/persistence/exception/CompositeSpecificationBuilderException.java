package edu.softserve.zoo.persistence.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.persistence.provider.specification_processing.builder.CompositeSpecificationBuilder;

/**
 * Report about all kinds of problems related to {@link CompositeSpecificationBuilder}
 *
 * @author Vadym Holub
 */
public class CompositeSpecificationBuilderException extends ApplicationException {

    public CompositeSpecificationBuilderException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        NO_SUCH_FIELD("reason.persistence.composite_specification_builder.no_such_field");

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
