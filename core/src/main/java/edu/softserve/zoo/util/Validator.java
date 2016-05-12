package edu.softserve.zoo.util;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

import java.util.Collection;

/**
 * <p>This utility class helps to validate data in application workflow</p>
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
     * @param object    the object for validation.
     * @param exception an exception that will be thrown if the {@code object} is {@code null}.
     * @param <T>       the object type.
     * @param <E>       the exception type.
     * @return the validated object.
     */
    public static <T, E extends ApplicationException> T notNull(T object, E exception) {
        if (object == null) {
            throw exception;
        }

        return object;
    }

    /**
     * <p>Validate that the specified argument is not {@code null};
     * otherwise throwing the defined {@link ApplicationException} subtype</p>
     *
     * @param object          the object for validation.
     * @param exceptionType   the type of an exception that will be thrown if the {@code object} is {@code null}.
     * @param exceptionReason exception reason if the {@code object} is {@code null}.
     * @param message         exception message if the {@code object} is {@code null}.
     * @param exceptionCause  exception cause if the {@code object} is {@code null}.
     * @param <T>             the object type.
     * @param <E>             the exception type.
     * @return the validated object.
     */
    public static <T, E extends ApplicationException> T notNull(T object,
                                                                Class<E> exceptionType,
                                                                ExceptionReason exceptionReason,
                                                                String message,
                                                                Throwable exceptionCause) {
        if (object == null) {
            throw buildException(exceptionType, exceptionReason, message, exceptionCause);
        }

        return object;
    }

    /**
     * <p>Validate that the specified argument is not {@code null};
     * otherwise throwing the defined {@link ApplicationException} subtype</p>
     *
     * @param object          the object for validation.
     * @param exceptionType   the type of an exception that will be thrown if the {@code object} is {@code null}.
     * @param exceptionReason exception reason if the {@code object} is {@code null}.
     * @param message         exception message if the {@code object} is {@code null}.
     * @param <T>             the object type.
     * @param <E>             the exception type.
     * @return the validated object.
     */
    public static <T, E extends ApplicationException> T notNull(T object,
                                                                Class<E> exceptionType,
                                                                ExceptionReason exceptionReason,
                                                                String message) {
        return notNull(object, exceptionType, exceptionReason, null);
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
     * <p>Validate that the argument condition is {@code true}. In other case
     * throwing the defined {@link ApplicationException} subtype.</p>
     *
     * @param expression      the boolean expression to check
     * @param exceptionType   an exception type that will be thrown if expression is {@code false}
     * @param exceptionReason exception reason if expression is {@code false}
     * @param message         exception message if expression is {@code false}
     * @param exceptionCause  exception cause if expression is {@code false}
     * @param <E>             the exception type.
     */
    public static <E extends ApplicationException> void isTrue(boolean expression,
                                                               Class<E> exceptionType,
                                                               ExceptionReason exceptionReason,
                                                               String message,
                                                               Throwable exceptionCause) {
        if (expression == false) {
            throw buildException(exceptionType, exceptionReason, message, exceptionCause);
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}. In other case
     * throwing the defined {@link ApplicationException} subtype.</p>
     *
     * @param expression      the boolean expression to check
     * @param exceptionType   an exception type that will be thrown if expression is {@code false}
     * @param exceptionReason exception reason if expression is {@code false}
     * @param message         exception message if expression is {@code false}
     * @param <E>             the exception type.
     */
    public static <E extends ApplicationException> void isTrue(boolean expression,
                                                               Class<E> exceptionType,
                                                               ExceptionReason exceptionReason,
                                                               String message) {
        isTrue(expression, exceptionType, exceptionReason, null);
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
     * @return the validated collection.
     */
    public static <T extends Collection<?>, E extends ApplicationException> T notEmpty(T collection, E exception) {
        notNull(collection, exception);

        if (collection.isEmpty()) {
            throw exception;
        }

        return collection;
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null}
     * nor empty. In other case throwing the defined {@link ApplicationException} subtype
     * exception.</p>
     *
     * @param collection      the collection for validation.
     * @param exceptionType   the type of an exception that will be thrown if the validation fails.
     * @param exceptionReason exception reason if the validation fails.
     * @param message         exception message if validation fails.
     * @param exceptionCause  exception cause if validation fails.
     * @param <T>             the collection type.
     * @param <E>             the exception type.
     * @return the validated collection.
     */
    public static <T extends Collection<?>, E extends ApplicationException> T notEmpty(T collection,
                                                                                       Class<E> exceptionType,
                                                                                       ExceptionReason exceptionReason,
                                                                                       String message,
                                                                                       Throwable exceptionCause) {
        notNull(collection, exceptionType, exceptionReason, message, exceptionCause);

        if (collection.isEmpty()) {
            throw buildException(exceptionType, exceptionReason, message, exceptionCause);
        }

        return collection;
    }

    /**
     * <p>Validate that the specified argument collection is neither {@code null}
     * nor empty. In other case throwing the defined {@link ApplicationException} subtype
     * exception.</p>
     *
     * @param collection      the collection for validation.
     * @param exceptionType   the type of an exception that will be thrown if the validation fails.
     * @param exceptionReason exception reason if the validation fails.
     * @param message         exception message if validation fails.
     * @param <T>             the collection type.
     * @param <E>             the exception type.
     * @return the validated collection.
     */
    public static <T extends Collection<?>, E extends ApplicationException> T notEmpty(T collection,
                                                                                       Class<E> exceptionType,
                                                                                       ExceptionReason exceptionReason,
                                                                                       String message) {
        return notEmpty(collection, exceptionType, exceptionReason, message, null);
    }

    private static <E extends ApplicationException> ApplicationException buildException(Class<E> exceptionType,
                                                                                        ExceptionReason exceptionReason,
                                                                                        String message,
                                                                                        Throwable exceptionCause) {
        return ApplicationException.getBuilderFor(exceptionType)
                .forReason(exceptionReason)
                .withMessage(message)
                .causedBy(exceptionCause)
                .build();
    }
}
