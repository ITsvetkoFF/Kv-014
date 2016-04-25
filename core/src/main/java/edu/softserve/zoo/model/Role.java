package edu.softserve.zoo.model;

import java.util.Objects;

public class Role extends BaseEntity {
    private Type type;

    public Role() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + getId() +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return type == role.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public enum Type {
        VISITOR, EMPLOYEE, MANAGER, ADMIN
    }
}
