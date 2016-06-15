package edu.softserve.zoo.web.validation;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Defines the behaviour of processing {@link FieldError}.
 *
 * @author Andrii Abramov on 6/9/16.
 */
public interface InvalidRequestProcessor<T> {

    T processFieldErrors(List<FieldError> errors);

}
