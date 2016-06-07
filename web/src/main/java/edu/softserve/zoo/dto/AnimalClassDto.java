package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsFieldDescription;
import edu.softserve.zoo.model.AnimalClass;
import edu.softserve.zoo.annotation.Dto;

import java.util.Objects;

@Dto(AnimalClass.class)
public class AnimalClassDto extends BaseDto {
    @DocsFieldDescription("The name")
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
