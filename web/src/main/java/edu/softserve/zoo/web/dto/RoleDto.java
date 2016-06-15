package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.core.model.Role;
import edu.softserve.zoo.web.annotation.DocsFieldDescription;
import edu.softserve.zoo.web.annotation.Dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

// TODO - verify if we need RoleDto at all.
@Dto(Role.class)
public class RoleDto extends BaseDto {

    @DocsFieldDescription("The type of role")
    @NotNull
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
