package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * @author Vadym Holub
 */
public class ZooZoneException extends ApplicationException {

    protected ZooZoneException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        CAPACITY_IS_LESS_THAN_CURRENT("reason.service.zoo-zone.capacity_is_less_than_current");

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
