package edu.softserve.zoo.dto;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.annotation.Dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Dto(Employee.class)
public class EmployeeDto extends BaseDto {
    private String firstName;
    private String lastName;
    private LocalDate employmentDate;
    private String email;
    private Set<RoleDto> roles;

    public EmployeeDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employmentDate=" + employmentDate +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto employeeDto = (EmployeeDto) o;
        return Objects.equals(firstName, employeeDto.firstName) &&
                Objects.equals(lastName, employeeDto.lastName) &&
                Objects.equals(employmentDate, employeeDto.employmentDate) &&
                Objects.equals(email, employeeDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, employmentDate, email);
    }
}
