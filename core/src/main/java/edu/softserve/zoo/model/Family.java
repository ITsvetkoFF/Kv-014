package edu.softserve.zoo.model;

import java.util.Objects;

public class Family extends BaseEntity {
    private String name;
    private AnimalClass animalClass;

    public Family() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalClass getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(AnimalClass animalClass) {
        this.animalClass = animalClass;
    }

    @Override
    public String toString() {
        return "Family{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", animalClass=" + animalClass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Family family = (Family) o;
        return Objects.equals(name, family.name) &&
                Objects.equals(animalClass, family.animalClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, animalClass);
    }
}
