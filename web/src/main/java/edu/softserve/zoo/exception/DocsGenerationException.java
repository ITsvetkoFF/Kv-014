package edu.softserve.zoo.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

public class DocsGenerationException extends ApplicationException {
    public DocsGenerationException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }
}
