package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * @author Vadym Holub
 */
public class GeographicalZoneException extends ApplicationException {
    /**
     * Constructs application exception instance.
     *
     * @param message specific details about the exception
     * @param reason  the reason this exception is thrown
     * @param cause   the throwable that caused this exception to get thrown
     */
    protected GeographicalZoneException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        SAVE_IS_NOT_SUPPORTED("reason.service.geo_zone.save_is_not_supported"),
        UPDATE_IS_NOT_SUPPORTED("reason.service.geo_zone.update_is_not_supported");

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
