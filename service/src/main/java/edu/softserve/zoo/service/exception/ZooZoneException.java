package edu.softserve.zoo.service.exception;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;

/**
 * @author Vadym Holub
 */
public class ZooZoneException extends ApplicationException {

    protected ZooZoneException(String message, ExceptionReason reason, Throwable cause) {
        super(message, reason, cause);
    }

    public enum Reason implements ExceptionReason {
        CAPACITY_IS_LESS_THAN_CURRENT("reason.service.zoo-zone.capacity_is_less_than_current"),
        GEO_ZONE_CANNOT_BE_UPDATED("reason.service.zoo-zone.geo_zone_cannot_be_updated"),
        ZOO_ZONE_CONTAINS_HOUSES("reason.service.zoo_zone_contains_houses"),
        CREATE_FAILED("reason.service.zoo_zone.failed_to_save"),
        DELETE_FAILED("reason.service.zoo_zone.failed_to_delete"),
        UPDATE_FAILED("reason.service.zoo_zone.failed_to_update");

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
