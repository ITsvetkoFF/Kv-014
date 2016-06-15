package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.core.model.Employee;
import edu.softserve.zoo.web.annotation.DocsFieldDescription;
import edu.softserve.zoo.web.annotation.Dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Dto(Employee.class)
public class EmployeeDto extends BaseDto {
    @DocsFieldDescription("First name")
    private String firstName;
    @DocsFieldDescription("Last name")
    private String lastName;
    @DocsFieldDescription("The date of employment")
    private LocalDate employmentDate;
    @DocsFieldDescription("E-mail")
    private String email;
    @DocsFieldDescription("The roles of employee")
    private Set<Object> roles;

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

    public Set<Object> getRoles() {
        return roles;
    }

    public void setRoles(Set<Object> roles) {
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
