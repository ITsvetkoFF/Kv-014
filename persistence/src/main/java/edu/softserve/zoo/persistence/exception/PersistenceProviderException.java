package edu.softserve.zoo.persistence.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * Serves to determine exceptional situations which could occur in PersistenceProvider's logic
 *
 * @author Vadym Holub
 */
public class PersistenceProviderException extends ApplicationException {

    public PersistenceProviderException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        SPECIFICATION_IS_NULL("reason.persistence.specification_is_null"),
        HIBERNATE_QUERY_FAILED("reason.persistence.hibernate_query_failed"),
        UNABLE_TO_FIND_STRATEGY("reason.persistence.unable_to_find_strategy"),
        JDBC_TEMPLATE_QUERY_FAILED("reason.persistence.jdbc_template_query_failed");

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
