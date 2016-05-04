package edu.softserve.zoo.exceptions;

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
