package edu.softserve.zoo.core.exceptions;

/**
 * This interface serves to define client-oriented explanation for {@link ApplicationException}
 *
 * @author Vadym Holub
 */
public interface ExceptionReason {

    /**
     * @return message for client's purposes
     * */
    String getMessage();

}
