package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsDescription;
import edu.softserve.zoo.model.Family;
import edu.softserve.zoo.annotation.Dto;

import java.util.Objects;

@Dto(Family.class)
public class FamilyDto extends BaseDto {
    @DocsDescription("The name")
    private String name;
    @DocsDescription("The animal")
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
