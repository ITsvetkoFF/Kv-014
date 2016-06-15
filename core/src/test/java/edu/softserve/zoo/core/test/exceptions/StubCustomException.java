package edu.softserve.zoo.core.test.exceptions;

import edu.softserve.zoo.core.exceptions.ApplicationException;
import edu.softserve.zoo.core.exceptions.ExceptionReason;

/**
 * Custom exception used in exception builder tests.
 *
 * @author Julia Siroshtan
 */
class StubCustomException extends ApplicationException {
    protected StubCustomException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }
}
