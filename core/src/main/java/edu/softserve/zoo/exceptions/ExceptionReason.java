package edu.softserve.zoo.exceptions;

/**
 * This class defines possible reasons that cause application exceptions to be thrown.
 *
 * @author Julia Siroshtan
 */
public enum ExceptionReason {

    NOT_FOUND(404),
    BAD_REQUEST(400);

    private final int code;

    ExceptionReason(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
