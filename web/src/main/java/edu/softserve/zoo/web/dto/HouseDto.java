package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.core.model.House;
import edu.softserve.zoo.web.annotation.DocsFieldDescription;
import edu.softserve.zoo.web.annotation.Dto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Dto(House.class)
public class HouseDto extends BaseDto {

    @DocsFieldDescription("The zone")
    @NotNull
    private ZooZoneDto zone;

    @DocsFieldDescription("The name")
    @NotNull
    @NotEmpty
    @Length(max = 50)
    private String name;

    @DocsFieldDescription("The max capacity of house")
    @NotNull
    private Integer maxCapacity;

    public HouseDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZooZoneDto getZone() {
        return zone;
    }

    public void setZone(ZooZoneDto zone) {
        this.zone = zone;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return "HouseDto{" +
                "id=" + getId() +
                ", name=" + name +
                ", zone=" + zone +
                ", maxCapacity=" + maxCapacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseDto houseDto = (HouseDto) o;
        return Objects.equals(zone, houseDto.zone) &&
                Objects.equals(name, houseDto.name) &&
                Objects.equals(maxCapacity, houseDto.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, name, maxCapacity);
    }
}
