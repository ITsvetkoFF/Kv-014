package edu.softserve.zoo.dto;

import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.Species;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Serhii Alekseichenko
 */
public class AnimalDto extends BaseDto {
    private String nickname;
    private Species species;
    private House house;
    private LocalDate birthday;
    private Integer temperatureMin;
    private Integer temperatureMax;
    private Integer animalsPerHouse;
    private Integer foodConsumption;

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

    public Integer getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(Integer foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDto animalDto = (AnimalDto) o;
        return Objects.equals(nickname, animalDto.nickname) &&
                Objects.equals(species, animalDto.species) &&
                Objects.equals(house, animalDto.house) &&
                Objects.equals(birthday, animalDto.birthday) &&
                Objects.equals(temperatureMin, animalDto.temperatureMin) &&
                Objects.equals(temperatureMax, animalDto.temperatureMax) &&
                Objects.equals(animalsPerHouse, animalDto.animalsPerHouse) &&
                Objects.equals(foodConsumption, animalDto.foodConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, species, house, birthday, temperatureMin, temperatureMax, animalsPerHouse, foodConsumption);
    }
}
