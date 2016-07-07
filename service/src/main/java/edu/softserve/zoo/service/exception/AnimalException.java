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
        LONG_NICKNAME("reason.service.animal.long_nickname"),
        BIRTHDAY_CHANGED("reason.service.animal.birthday_changed"),
        WRONG_BIRTHDAY("reason.service.animal.wrong_birthday"),
        WRONG_FOOD_CONSUMPTION("reason.service.animal.wrong_food_consumption"),
        WRONG_HOUSE("reason.service.animal.wrong_house"),
        WRONG_SPECIES("reason.service.animal.wrong_species"),
        UPDATE_FAILED("reason.service.animal.update_failed"),
        SAVE_FAILED("reason.service.animal.save_failed"),
        ANIMAL_IS_NULL("reason.service.animal.animal_is_null");

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
