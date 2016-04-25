package edu.softserve.zoo.model;

import java.time.LocalDate;
import java.util.Objects;

public class Animal extends BaseEntity {
    private String nickname;
    private Species species;
    private House house;
    private LocalDate birthday;
    private Integer temperatureMin;
    private Integer temperatureMax;
    private Integer animalsPerHouse;

    public Animal() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Integer temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Integer getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Integer temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Integer getAnimalsPerHouse() {
        return animalsPerHouse;
    }

    public void setAnimalsPerHouse(Integer animalsPerHouse) {
        this.animalsPerHouse = animalsPerHouse;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + getId() +
                ", nickname='" + nickname + '\'' +
                ", species=" + species +
                ", house=" + house +
                ", birthday=" + birthday +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                ", animalsPerHouse=" + animalsPerHouse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(nickname, animal.nickname) &&
                Objects.equals(species, animal.species) &&
                Objects.equals(house, animal.house) &&
                Objects.equals(birthday, animal.birthday) &&
                Objects.equals(temperatureMin, animal.temperatureMin) &&
                Objects.equals(temperatureMax, animal.temperatureMax) &&
                Objects.equals(animalsPerHouse, animal.animalsPerHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, species, house, birthday, temperatureMin, temperatureMax, animalsPerHouse);
    }
}
