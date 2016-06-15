package edu.softserve.zoo.core.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "animals")
public class Animal extends BaseEntity {

    private String nickname;
    private Species species;
    private House house;
    private LocalDate birthday;
    private Integer temperatureMin;
    private Integer temperatureMax;
    private Integer animalsPerHouse;
    private Integer foodConsumption;

    public Animal() {
    }

    @Column(name = "nickname", length = 50)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id", nullable = false)
    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Column(name = "birthday")
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Column(name = "temperature_min", nullable = false)
    public Integer getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Integer temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    @Column(name = "temperature_max", nullable = false)
    public Integer getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Integer temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    @Column(name = "food_consumption", nullable = false)
    public Integer getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(Integer foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    @Column(name = "animals_per_house", nullable = false)
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
                ", foodConsumption=" + foodConsumption +
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
                Objects.equals(foodConsumption, animal.foodConsumption) &&
                Objects.equals(animalsPerHouse, animal.animalsPerHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, species, house, birthday, temperatureMin, temperatureMax, foodConsumption, animalsPerHouse);
    }
}
