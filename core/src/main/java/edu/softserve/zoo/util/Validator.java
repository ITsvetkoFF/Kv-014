package edu.softserve.zoo.util;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

import java.util.Collection;
import java.util.Set;

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
        if (!expression) {
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

    /**
     * <p>Validate that the specified argument is not {@code null};
     * otherwise add defined {@link ExceptionReason} to causes set</p>
     *
     * @param value  the value for validation.
     * @param reason an exception reason.
     * @param causes reasons set.
     */
    public static void notNull(Object value, ExceptionReason reason, Set<ExceptionReason> causes) {
        if (value == null) {
            causes.add(reason);
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}.
     * Otherwise add defined {@link ExceptionReason} to causes set</p>
     *
     * @param expression the boolean expression to check.
     * @param reason     an exception reason.
     * @param causes     reasons set.
     */
    public static void isTrue(boolean expression, ExceptionReason reason, Set<ExceptionReason> causes) {
        if (!expression) {
            causes.add(reason);
        }
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null}
     * nor empty. Otherwise add defined {@link ExceptionReason} to causes set</p>
     *
     * @param collection the collection for validation.
     * @param reason     an exception reason.
     * @param <T>        reasons set.
     */
    public static <T extends Collection<?>> void notEmpty(T collection, ExceptionReason reason, Set<ExceptionReason> causes) {
        notNull(collection, reason, causes);

        if (collection.isEmpty()) {
            causes.add(reason);
        }
    }
}
