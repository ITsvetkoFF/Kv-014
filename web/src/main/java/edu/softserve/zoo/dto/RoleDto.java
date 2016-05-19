package edu.softserve.zoo.dto;

import java.util.Objects;
// TODO - verify if we need RoleDto at all.
public class RoleDto extends BaseDto {
    private Type type;

    public RoleDto() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + getId() +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return type == roleDto.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public enum Type {
        VISITOR, EMPLOYEE, MANAGER, ADMIN
    }
}
