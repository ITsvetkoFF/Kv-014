package edu.softserve.zoo;

import edu.softserve.zoo.exceptions.ApplicationException;

public class Error {

    private String message;

    public Error(ApplicationException exception) {
        this.message = exception.getReason().getMessage();
    }

    public String getMessage() {
        return message;
    }
}
