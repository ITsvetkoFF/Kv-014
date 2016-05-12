package edu.softserve.zoo.dto;

import java.util.Objects;

/**
 * @author Julia Siroshtan
 */
public class RoleDto extends BaseDto {

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        VISITOR, EMPLOYEE, MANAGER, ADMIN
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDto roleDto = (RoleDto) o;
        return type == roleDto.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "type=" + type +
                '}';
    }
}
