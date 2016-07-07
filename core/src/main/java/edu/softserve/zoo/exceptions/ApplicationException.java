package edu.softserve.zoo.exceptions;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a common superclass of all exceptions thrown by application.
 * This exception and its subclasses are unchecked exceptions.
 * {@code ApplicationException} class includes builder for exception instances.
 *
 * @author Julia Siroshtan
 */
public abstract class ApplicationException extends RuntimeException {

    /**
     * Reason this exception is thrown.
     * Possible values defined in {@code ExceptionReason} enum.
     */
    private ExceptionReason reason;

    /**
     * Set of Qualification reasons.
     * Possible values defined in {@code ExceptionReason} enum.
     */
    private Set<ExceptionReason> qualificationReasons;

    /**
     * Constructs application exception instance.
     *
     * @param message specific details about the exception
     * @param reason  the reason this exception is thrown
     * @param cause   the throwable that caused this exception to get thrown
     */
    protected ApplicationException(final String message, final ExceptionReason reason, final Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    /**
     * Returns builder object for exceptions instance creation.
     *
     * @param exceptionClass application exception class
     * @return exception instance builder object
     */
    public static Builder getBuilderFor(final Class<? extends ApplicationException> exceptionClass) {
        return new Builder(exceptionClass);
    }

    public ExceptionReason getReason() {
        return reason;
    }

    public void setReason(final ExceptionReason reason) {
        this.reason = reason;
    }

    public Set<ExceptionReason> getQualificationReasons() {
        return qualificationReasons;
    }

    public void setQualificationReasons(Set<ExceptionReason> qualificationReasons) {
        this.qualificationReasons = qualificationReasons;
    }

    /**
     * This class is used to create application exception object.
     * To use this class, get its instance by calling {@code ApplicationException.getBuilderFor()} method,
     * set available exception properties (message, reason, cause), and call build() method.
     */
    public static class Builder {

        /**
         * Application exception class.
         */
        private Class<? extends ApplicationException> exceptionClass;
        /**
         * Specific details about the exception.
         */
        private String message;
        /**
         * The throwable that caused this exception to get thrown.
         */
        private Throwable cause;
        /**
         * Reason this exception is thrown.
         * Possible values defined in {@code ExceptionReason} enum.
         */
        private ExceptionReason reason;

        /**
         * Set of Qualification reasons.
         * Possible values defined in {@code ExceptionReason} enum.
         */
        private Set<ExceptionReason> qualificationReasons;

        /**
         * This class should be instantiated via fabric method {@code ApplicationException.getBuilderFor()} method,
         * so default constructor is hidden.
         */
        private Builder() {
            throw new UnsupportedOperationException("Use ApplicationException.getBuilderFor() method");
        }

        /**
         * Constructs this builder object.
         *
         * @param exceptionClass application exception class.
         */
        public Builder(final Class<? extends ApplicationException> exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        /**
         * Sets specific details about the exception.
         *
         * @param message exception message
         * @return this builder instance
         */
        public Builder withMessage(final String message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the throwable that caused this exception to get thrown.
         *
         * @param cause exception cause
         * @return this builder instance
         */
        public Builder causedBy(final Throwable cause) {
            this.cause = cause;
            return this;
        }

        /**
         * Sets the reason this exception is thrown.
         *
         * @param reason exception reason
         * @return this builder instance
         */
        public Builder forReason(final ExceptionReason reason) {
            this.reason = reason;
            return this;
        }

        /**
         * Sets the qualification reason this exception is thrown.
         *
         * @param reason exception reason
         * @return this builder instance
         */
        public Builder withCause(final ExceptionReason reason) {
            if (qualificationReasons == null) {
                qualificationReasons = new HashSet<>();
            }
            qualificationReasons.add(reason);
            return this;
        }

        /**
         * Build exception instance. Its type will be defined by {@code exceptionClass} property.
         *
         * @return exception object
         */
        public ApplicationException build() {
            try {
                Constructor<? extends ApplicationException> constructor =
                        exceptionClass.getDeclaredConstructor(String.class, ExceptionReason.class, Throwable.class);
                constructor.setAccessible(true);
                ApplicationException applicationException = constructor.newInstance(message, reason, cause);
                applicationException.setQualificationReasons(qualificationReasons);
                return applicationException;
            } catch (ReflectiveOperationException e) {
                throw new ExceptionBuilderException("Can't instantiate " + exceptionClass,
                        ExceptionBuilderException.Reason.BUILDER_FAILS, e);
            }
        }
    }
}
