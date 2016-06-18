package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employee_roles")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class Role extends BaseEntity {

    @Enumerated
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Type type;

    public enum Type {
        VISITOR, EMPLOYEE, MANAGER, ADMIN;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
