package edu.softserve.zoo.dto;

import java.util.Objects;

/**
 * Created by Julia Avdeionok
 */
public class ZooZoneDto extends BaseDto{
    private String name;
    private String description;
    private Integer houseCapacity;
    private GeographicalZoneDto geographicalZoneDto;

    public ZooZoneDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHouseCapacity() {
        return houseCapacity;
    }

    public void setHouseCapacity(Integer houseCapacity) {
        this.houseCapacity = houseCapacity;
    }

    public GeographicalZoneDto getGeographicalZoneDto() {
        return geographicalZoneDto;
    }

    public void setGeographicalZoneDto(GeographicalZoneDto geographicalZoneDto) {
        this.geographicalZoneDto = geographicalZoneDto;
    }

    @Override
    public String toString() {
        return "ZooZoneDto{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", houseCapacity=" + houseCapacity +
                ", geographicalZoneDto=" + geographicalZoneDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZooZoneDto zooZoneDto = (ZooZoneDto) o;
        return Objects.equals(name, zooZoneDto.name) &&
                Objects.equals(description, zooZoneDto.description) &&
                Objects.equals(houseCapacity, zooZoneDto.houseCapacity) &&
                Objects.equals(geographicalZoneDto, zooZoneDto.geographicalZoneDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, houseCapacity, geographicalZoneDto);
    }
}
