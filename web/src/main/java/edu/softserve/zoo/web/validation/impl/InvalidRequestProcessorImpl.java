package edu.softserve.zoo.web.validation.impl;

import edu.softserve.zoo.web.dto.InvalidRequestDto;
import edu.softserve.zoo.web.validation.FieldError;
import edu.softserve.zoo.web.validation.InvalidRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

/**
 * Implementation of {@link InvalidRequestProcessor} that converts incorrect user input into
 * message with such structure:
 * <pre>
 *  {
 *      "errors": [
 *          {
 *              "fieldName": "...",
 *              "rejectedValue": "...",
 *              "validationMessage":"..." // i18n included
 *          }, ...
 *      ]
 *  }
 * </pre>
 * <p>
 * This processor also deals with i18n.
 *
 * @author Andrii Abramov on 6/9/16.
 */
public class InvalidRequestProcessorImpl implements InvalidRequestProcessor<InvalidRequestDto> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public InvalidRequestDto processFieldErrors(List<org.springframework.validation.FieldError> errors) {
        final InvalidRequestDto result = new InvalidRequestDto();
        errors.stream()
                .map(error -> new FieldError(error.getField(), error.getRejectedValue(), this.resolveMessage(error)))
                .forEach(result::addFieldError);

        return result;
    }

    private String resolveMessage(org.springframework.validation.FieldError fieldError) {
        // TODO for guy who will deal with i18n
        return messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
    }

}
