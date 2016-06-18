package edu.softserve.zoo.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.converter.ModelConverter;

/**
 * ModelConverterException signals that problem has occurred during working process of {@link ModelConverter}
 *
 * @author Vadym Holub
 */
public class ModelConverterException extends ApplicationException {

    public ModelConverterException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {

        MAPPING_STRATEGY_NOT_FOUND("reason.web.mapping_strategy_not_found"),
        MAPPING_TO_DTO_FAILED("reason.web.mapping_to_dto_failed"),
        MAPPING_TO_ENTITY_FAILED("reason.web.mapping_to_entity_failed");

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
