package edu.softserve.zoo.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class EmployeeDto extends BaseDto {
    private String firstName;
    private String lastName;
    private LocalDate employmentDate;
    private String email;
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
