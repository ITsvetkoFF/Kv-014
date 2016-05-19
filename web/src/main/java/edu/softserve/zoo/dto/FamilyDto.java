package edu.softserve.zoo.dto;

import java.util.Objects;

public class FamilyDto extends BaseDto {
    private String name;
    private AnimalClassDto animalClassDto;

    public FamilyDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalClassDto getAnimalClassDto() {
        return animalClassDto;
    }

    public void setAnimalClassDto(AnimalClassDto animalClassDto) {
        this.animalClassDto = animalClassDto;
    }

    @Override
    public String toString() {
        return "FamilyDto{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", animalClassDto=" + animalClassDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyDto familyDto = (FamilyDto) o;
        return Objects.equals(name, familyDto.name) &&
                Objects.equals(animalClassDto, familyDto.animalClassDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, animalClassDto);
    }
}
