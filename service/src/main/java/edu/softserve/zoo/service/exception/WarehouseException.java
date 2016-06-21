package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.model.Warehouse;

/**
 * WarehouseException serves to determine exceptional situations which are related to {@link Warehouse}'s business logic
 *
 * @author Vadym Holub
 */
public class WarehouseException extends ApplicationException {

    protected WarehouseException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        AMOUNT_GREATER_THAN_CAPACITY("reason.service.invalid_data_provided");

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
