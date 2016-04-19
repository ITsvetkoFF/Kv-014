package edu.softserv.zoo.persistence.exceptions;

/**
 * Classname: PersistenceException
 * Version: 1.0
 * Created: 19.04.2016
 */
public class PersistenceException extends Exception {

    public PersistenceException() {
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}