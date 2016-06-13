package edu.softserve.zoo.test.endpoints.checking;

import org.apache.commons.lang3.Validate;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The result of the performed check operation. If this result
 * has error massages, then some errors occurred during check.
 *
 * @author Bohdan Cherniakh
 */
public class CheckResult {
    private static final String MESSAGE_SEPARATOR = "\n";

    private List<String> errorMessages = new LinkedList<>();

    /**
     * Adds an error to the result.
     *
     * @param description brief description of an error.
     * @throws NullPointerException in case when description is null.
     */
    public void addError(String description) {
        Validate.notNull(description);
        errorMessages.add(description);
    }

    /**
     * Indicates whether the error occurred.
     *
     * @return {@code true} if errors occurred.
     */
    public boolean hasErrors() {
        return !errorMessages.isEmpty();
    }

    /**
     * Return the description of occurred errors.
     *
     * @return descriptions of occurred errors during check.
     */
    public String getErrorMessage() {
        return errorMessages.stream().collect(Collectors.joining(MESSAGE_SEPARATOR));
    }

}
