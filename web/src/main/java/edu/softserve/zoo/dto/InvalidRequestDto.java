package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.IrrespectiveDto;
import edu.softserve.zoo.validation.FieldError;

import java.util.ArrayList;
import java.util.HashSet;
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

    private final List<FieldError> errors = new ArrayList<>();

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
        final InvalidRequestDto that = (InvalidRequestDto) o;

        final List<FieldError> thatErrors = that.getErrors();

        final boolean equalLength = thatErrors.size() == errors.size();

        return equalLength && new HashSet<>(errors).containsAll(new HashSet<>(thatErrors));
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors);
    }

    @Override
    public String toString() {
        return "InvalidRequestDto{" +
                "errors=" + errors +
                '}';
    }
}
