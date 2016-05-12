package edu.softserve.zoo.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * @author Julia Siroshtan
 */
public class EmployeeDto extends BaseDto {

    private String firstName;
    private String lastName;
    private LocalDate employmentDate;
    private String email;

    @JsonSerialize(contentUsing = Serializer.class)
    @JsonDeserialize(contentUsing = Deserializer.class)
    private Set<RoleDto> role;

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

    public Set<RoleDto> getRole() {
        return role;
    }

    public void setRole(Set<RoleDto> role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(employmentDate, that.employmentDate) &&
                Objects.equals(email, that.email) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, employmentDate, email, role);
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employmentDate=" + employmentDate +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    private static class Serializer extends JsonSerializer<RoleDto> {
        @Override
        public void serialize(RoleDto value, JsonGenerator generator, SerializerProvider serializer)
                throws IOException {
            generator.writeString(value.getType().toString());
        }
    }

    private static class Deserializer extends JsonDeserializer<RoleDto> {
        @Override
        public RoleDto deserialize(JsonParser parser, DeserializationContext context)
                throws IOException {
            JsonNode node = parser.getCodec().readTree(parser);
            String roleTitle = node.asText();
            RoleDto.Type type = RoleDto.Type.valueOf(roleTitle);
            Integer id = type.ordinal();
            RoleDto result = new RoleDto();
            result.setId(id);
            result.setType(type);
            return result;
        }
    }
}
