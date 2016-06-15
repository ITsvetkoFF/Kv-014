package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.core.model.Family;
import edu.softserve.zoo.web.annotation.DocsFieldDescription;
import edu.softserve.zoo.web.annotation.Dto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Dto(Family.class)
public class FamilyDto extends BaseDto {

    @DocsFieldDescription("The name")
    @NotNull
    @NotEmpty
    @Length(max = 50)
    private String name;

    @DocsFieldDescription("The animal")
    @NotNull
    private AnimalClassDto animalClass;

    public FamilyDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalClassDto getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(AnimalClassDto animalClass) {
        this.animalClass = animalClass;
    }

    @Override
    public String toString() {
        return "FamilyDto{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", animalClass=" + animalClass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyDto familyDto = (FamilyDto) o;
        return Objects.equals(name, familyDto.name) &&
                Objects.equals(animalClass, familyDto.animalClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, animalClass);
    }
}
