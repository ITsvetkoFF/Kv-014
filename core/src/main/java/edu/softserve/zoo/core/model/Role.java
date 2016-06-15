package edu.softserve.zoo.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "employee_roles")
public class Role extends BaseEntity {

    private Type type;

    public Role() {
    }

    @Enumerated
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
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
        VISITOR, EMPLOYEE, MANAGER, ADMIN;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
