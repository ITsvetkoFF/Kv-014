package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsFieldDescription;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.annotation.Dto;

import java.util.Objects;

@Dto(House.class)
public class HouseDto extends BaseDto {
    @DocsFieldDescription("The zone")
    private ZooZoneDto zone;
    @DocsFieldDescription("The name")
    private String name;
    @DocsFieldDescription("The max capacity of house")
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
