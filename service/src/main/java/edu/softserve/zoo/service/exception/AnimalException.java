package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.model.Animal;

/**
 * AnimalException serves to determine exceptional situations which are related to {@link Animal}'s business logic
 *
 * @author Serhii Alekseichenko
 */
public class AnimalException extends ApplicationException {
    /**
     * Constructs application exception instance.
     *
     * @param message specific details about the exception
     * @param reason  the reason this exception is thrown
     * @param cause   the throwable that caused this exception to get thrown
     */
    protected AnimalException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        SPECIES_CHANGED("reason.service.animal.species_changed"),
        BIRTHDAY_CHANGED("reason.service.animal.birthday_changed"),
        WRONG_BIRTHDAY("reason.service.animal.wrong_birthday");

        private final String message;

        Reason(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
