package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.core.model.AnimalClass;
import edu.softserve.zoo.web.annotation.DocsFieldDescription;
import edu.softserve.zoo.web.annotation.Dto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Dto(AnimalClass.class)
public class AnimalClassDto extends BaseDto {
    @DocsFieldDescription("The name")

    @NotNull
    @NotEmpty
    @Length(max = 50)
    private String name;

    public AnimalClassDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AnimalClassDto{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalClassDto that = (AnimalClassDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
