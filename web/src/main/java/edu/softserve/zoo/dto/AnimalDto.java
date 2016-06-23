package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsFieldDescription;
import edu.softserve.zoo.annotation.Dto;
import edu.softserve.zoo.model.Animal;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Dto(Animal.class)
public class AnimalDto extends BaseDto {

    @DocsFieldDescription(value = "The nickname", optional = true)
    @Length(max = 50)
    private String nickname;

    @DocsFieldDescription("All species")
    @NotNull
    private SpeciesDto species;

    @DocsFieldDescription("The house")
    @NotNull
    private HouseDto house;

    @DocsFieldDescription("The birthday")
    private LocalDate birthday;

    @DocsFieldDescription("The food consumption")
    @NotNull
    @Min(1)
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

    public Integer getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(Integer foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    @Override
    public String toString() {
        return "AnimalDto{" +
                "id=" + getId() +
                ", nickname='" + nickname + '\'' +
                ", species=" + species +
                ", house=" + house +
                ", birthday=" + birthday +
                ", foodConsumption=" + foodConsumption +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalDto animal = (AnimalDto) o;
        return Objects.equals(nickname, animal.nickname) &&
                Objects.equals(species, animal.species) &&
                Objects.equals(house, animal.house) &&
                Objects.equals(birthday, animal.birthday) &&
                Objects.equals(foodConsumption, animal.foodConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, species, house, birthday, foodConsumption);
    }
}
