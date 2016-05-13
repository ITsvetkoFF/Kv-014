package edu.softserve.zoo.persistence.exception;


import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

public class NotFoundException extends ApplicationException {
    /**
     * Constructs application exception instance.
     *
     * @param message specific details about the exception
     * @param reason  the reason this exception is thrown
     * @param cause   the throwable that caused this exception to get thrown
     */
    protected NotFoundException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }
}
