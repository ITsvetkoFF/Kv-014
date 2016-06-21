package edu.softserve.zoo.exceptions;

/**
 * ValidationException is thrown when the provided data doesn't match validated requirements
 *
 * @author Vadym Holub
 */
public class ValidationException extends ApplicationException {

    protected ValidationException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        ARGUMENT_IS_NULL("reason.service.argument_is_null"),
        ENUM_MAPPING_FAILED("reason.web.enum_mapping_failed");

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
