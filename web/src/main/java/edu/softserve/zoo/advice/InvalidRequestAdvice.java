package edu.softserve.zoo.advice;

import edu.softserve.zoo.dto.InvalidRequestDto;
import edu.softserve.zoo.validation.InvalidRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class is used for handling exceptions related to invalid data provided by user.
 *
 * @author Andrii Abramov on 6/9/16.
 */
@ControllerAdvice
public class InvalidRequestAdvice {

    @Autowired
    private InvalidRequestProcessor<InvalidRequestDto> invalidRequestProcessor;

    /**
     * Handles the situation when user providel invalid request.
     * The trigger for validation is @{@link javax.validation.Valid} annotation.
     *
     * @param ex exception provided bt Spring
     * @return generated response with provided info
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public InvalidRequestDto handleInvalidRequest(MethodArgumentNotValidException ex) {
        return invalidRequestProcessor.processFieldErrors(ex.getBindingResult().getFieldErrors());
    }

}
