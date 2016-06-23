package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * TaskException for {@link edu.softserve.zoo.model.Task}'s business logic
 *
 * @author Julia Avdeionok
 */
public class TaskException extends ApplicationException {
    /**
     * Constructs application exception instance.
     *
     * @param message specific details about the exception
     * @param reason  the reason this exception is thrown
     * @param cause   the throwable that caused this exception to get thrown
     */
    protected TaskException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        TASK_ESTIMATED_TIME_IS_NOT_VALID("reason.service.invalid_data_provided");

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
