package edu.softserve.zoo.dto;

import edu.softserve.zoo.model.Role;

import java.util.Objects;

// TODO - verify if we need RoleDto at all.
public class RoleDto extends BaseDto {
    private Role.Type type;

    public RoleDto() {
    }

    public Role.Type getType() {
        return type;
    }

    public void setType(Role.Type type) {
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
}
