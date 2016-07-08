package edu.softserve.zoo;

public class Error {

    private String message;

    private String qualificationMessage;

    public Error(String message) {
        this.message = message;
    }

    public Error(String message, String qualificationMessage) {
        this.message = message;
        this.qualificationMessage = qualificationMessage;
    }

    public String getMessage() {
        return message;
    }

    public String getQualificationMessage() {
        return qualificationMessage;
    }
}
