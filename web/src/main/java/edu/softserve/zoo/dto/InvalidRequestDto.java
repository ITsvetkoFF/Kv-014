package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.IrrespectiveDto;
import edu.softserve.zoo.validation.FieldError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Dto that stores a collection of {@link FieldError}.
 * It is used when we have to provide information about invalid data provided by user.
 *
 * @author Andrii Abramov on 6/9/16.
 */
@IrrespectiveDto
public class InvalidRequestDto {

    private final List<FieldError> errors;

    public InvalidRequestDto(FieldError... errors) {
        this.errors = new ArrayList<>(Arrays.asList(errors));
    }

    public void addFieldError(FieldError error) {
        if (error != null) {
            this.errors.add(error);
        }
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidRequestDto that = (InvalidRequestDto) o;
        return Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors);
    }
}
