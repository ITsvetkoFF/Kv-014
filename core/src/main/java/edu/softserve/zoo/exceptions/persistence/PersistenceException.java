package edu.softserve.zoo.exceptions.persistence;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * <p>Base exception of the persistence module.
 * Reports about all kinds of problems.</p>
 *
 * @author Bohdan Cherniakh
 */
public class PersistenceException extends ApplicationException {

    public PersistenceException(final String message, final ExceptionReason reason, final Throwable cause) {
        super(message, reason, cause);
    }
}