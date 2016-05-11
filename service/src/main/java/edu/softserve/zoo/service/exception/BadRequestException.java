package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * Stands for:
 * The server cannot or will not process the request due to an apparent client error
 * (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).
 * @author Andrii Abramov on 11-May-16.
 */
public class BadRequestException extends ApplicationException {
    /**
     * Constructs application exception instance.
     *
     * @param message specific details about the exception
     * @param reason  the reason this exception is thrown
     * @param cause   the throwable that caused this exception to get thrown
     */
    protected BadRequestException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }
}
