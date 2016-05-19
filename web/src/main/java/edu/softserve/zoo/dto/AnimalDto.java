package edu.softserve.zoo.dto;

import java.time.LocalDate;
import java.util.Objects;

public class AnimalDto extends BaseDto {
    private String nickname;
    private SpeciesDto species;
    private HouseDto house;
    private LocalDate birthday;
    private Integer temperatureMin;
    private Integer temperatureMax;
    private Integer animalsPerHouse;
    private Integer foodConsumption;

    public AnimalDto() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public SpeciesDto getSpecies() {
        return species;
    }

    public void setSpecies(SpeciesDto species) {
        this.species = species;
    }

    public HouseDto getHouse() {
        return house;
    }

    public void setHouse(HouseDto house) {
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

    public Integer getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(Integer foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    public Integer getAnimalsPerHouse() {
        return animalsPerHouse;
    }

    public void setAnimalsPerHouse(Integer animalsPerHouse) {
        this.animalsPerHouse = animalsPerHouse;
    }


    @Override
    public String toString() {
        return "AnimalDto{" +
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
        AnimalDto animalDto = (AnimalDto) o;
        return Objects.equals(nickname, animalDto.nickname) &&
                Objects.equals(species, animalDto.species) &&
                Objects.equals(house, animalDto.house) &&
                Objects.equals(birthday, animalDto.birthday) &&
                Objects.equals(temperatureMin, animalDto.temperatureMin) &&
                Objects.equals(temperatureMax, animalDto.temperatureMax) &&
                Objects.equals(foodConsumption, animalDto.foodConsumption) &&
                Objects.equals(animalsPerHouse, animalDto.animalsPerHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, species, house, birthday, temperatureMin, temperatureMax, foodConsumption, animalsPerHouse);
    }
}
