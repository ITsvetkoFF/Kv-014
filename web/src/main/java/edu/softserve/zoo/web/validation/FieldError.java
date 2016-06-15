package edu.softserve.zoo.web.validation;

import java.util.Objects;

/**
 * Pojo for holding response with information about invalid data provided by user.
 *
 * @author Andrii Abramov on 6/9/16.
 */
public class FieldError {

    private String fieldName;
    private Object rejectedValue;
    private String validationMessage;


    public FieldError() {
    }


    public FieldError(String fieldName, Object rejectedValue, String validationMessage) {
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.validationMessage = validationMessage;
    }

    public String getFieldName() {
        return fieldName;
    }


    public Object getRejectedValue() {
        return rejectedValue;
    }


    public String getValidationMessage() {
        return validationMessage;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldError that = (FieldError) o;
        return Objects.equals(fieldName, that.fieldName) &&
                Objects.equals(rejectedValue, that.rejectedValue) &&
                Objects.equals(validationMessage, that.validationMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, rejectedValue, validationMessage);
    }

    @Override
    public String toString() {
        return "FieldError{" +
                "fieldName='" + fieldName + '\'' +
                ", rejectedValue=" + rejectedValue +
                ", validationMessage='" + validationMessage + '\'' +
                '}';
    }
}
