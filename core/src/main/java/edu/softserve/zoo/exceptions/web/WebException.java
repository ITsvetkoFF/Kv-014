package edu.softserve.zoo.exceptions.web;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * <p>Base exception of the web layer module.
 * Reports about all kinds of problems.</p>
 *
 * @author Serhii Alekseichenko
 */
public class WebException extends ApplicationException {

    public WebException(final String message, final ExceptionReason reason, final Throwable cause) {
        super(message, reason, cause);
    }
}