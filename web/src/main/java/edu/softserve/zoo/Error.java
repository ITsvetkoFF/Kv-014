package edu.softserve.zoo;

import java.util.Set;

public class Error {

    private String message;

    private Set<String> causes;

    public Error(String message) {
        this.message = message;
    }

    public Error(String message, Set<String> causes) {
        this.message = message;
        this.causes = causes;
    }

    public String getMessage() {
        return message;
    }

    public Set<String> getCauses() {
        return causes;
    }
}
