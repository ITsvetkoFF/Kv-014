package edu.softserve.zoo.persistence.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * @author Vadym Holub
 */
public class CompositeSpecificationBuilderProviderException extends ApplicationException {

    public CompositeSpecificationBuilderProviderException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        UNSUPPORTED_STRATEGY("reason.persistence.composite_specification_builder_provider.unsupported_strategy");

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
