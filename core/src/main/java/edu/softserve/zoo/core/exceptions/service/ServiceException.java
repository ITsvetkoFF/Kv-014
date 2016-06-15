package edu.softserve.zoo.core.exceptions.service;

import edu.softserve.zoo.core.exceptions.ApplicationException;
import edu.softserve.zoo.core.exceptions.ExceptionReason;

/**
 * Base exception of the service module.
 * Reports about all kinds of problems.
 *
 * @author Andrii Abramov on 6/15/16.
 */
public class ServiceException extends ApplicationException {


    /**
     * Constructs application exception instance.
     *
     * @param message specific details about the exception
     * @param reason  the reason this exception is thrown
     * @param cause   the throwable that caused this exception to get thrown
     */
    protected ServiceException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }
}
