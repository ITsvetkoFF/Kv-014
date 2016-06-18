package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "employees")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class Employee extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    @Column(name = "hired_date")
    private LocalDate employmentDate;
    @Column(name = "email", unique = true, length = 50)
    private String email;
    @Column(name = "password", length = 50)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_role_mapping",
            joinColumns = @JoinColumn(name = "employee_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
    private Set<Role> roles;

}
