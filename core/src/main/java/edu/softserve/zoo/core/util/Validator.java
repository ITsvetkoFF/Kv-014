package edu.softserve.zoo.core.util;

import edu.softserve.zoo.core.exceptions.ApplicationException;

import java.util.Collection;

/**
 * <p>This utility class helps to validate data in the application workflow</p>
 * <p>Validation based on the following principle:
 * <ul>
 * <li>An invalid argument causes a subtype of the {@link ApplicationException}</li>
 * </ul>
 *
 * @author Bohdan Cherniakh
 * @see ApplicationException
 */
public class Validator {

    /**
     * <p>Validate that the specified argument is not {@code null};
     * otherwise throwing the defined {@link ApplicationException} subtype</p>
     *
     * @param value     the value for validation.
     * @param exception an exception that will be thrown if the {@code value} is {@code null}.
     * @param <E>       the exception type.
     */
    public static <E extends ApplicationException> void notNull(Object value, E exception) {
        if (value == null) {
            throw exception;
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}. In other case
     * throwing the defined {@link ApplicationException} subtype.</p>
     *
     * @param expression the boolean expression to check
     * @param exception  an exception that will be thrown if expression is {@code false}
     * @param <E>        the exception type.
     */
    public static <E extends ApplicationException> void isTrue(boolean expression, E exception) {
        if (expression == false) {
            throw exception;
        }
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null}
     * nor empty. In other case throwing the defined {@link ApplicationException} subtype
     * exception.</p>
     *
     * @param collection the collection for validation.
     * @param exception  an exception that will be thrown if the validation fails.
     * @param <T>        the collection type.
     * @param <E>        the exception type.
     */
    public static <T extends Collection<?>, E extends ApplicationException> void notEmpty(T collection, E exception) {
        notNull(collection, exception);

        if (collection.isEmpty()) {
            throw exception;
        }
    }
}
