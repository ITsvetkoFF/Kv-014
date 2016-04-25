package edu.softserve.zoo.persistence.exception;

/**
 * <p>Base exception in the persistence module.
 * Reports about all kinds of problems.</p>
 * <p>Should be replaced by the exceptions from exceptions hierarchy</p>
 * @see <a href = "https://github.com/ITsvetkoFF/Kv-014/issues/43">Issue#43</a>
 */
public class PersistenceException extends RuntimeException {

    public PersistenceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(final String message) {
        super(message);
    }

    public PersistenceException(final Throwable cause) {
        super(cause);
    }
}