package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.exceptions.service.ServiceException;

/**
 * Use this Exception when user provided logically incorrect data.
 *
 * @author Andrii Abramov on 6/14/16.
 */
public class InvalidDataException extends ServiceException {

    /**
     * Constructs application exception instance.
     *
     * @param message specific details about the exception
     * @param reason  the reason this exception is thrown
     * @param cause   the throwable that caused this exception to get thrown
     */
    protected InvalidDataException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

}
