package edu.softserve.zoo.dto;

import java.util.Objects;

/**
 * @author Ilya Doroshenko
 */
public class ZooZoneDto extends BaseDto{
    private String name;
    private String description;
    private Integer houseCapacity;
    private GeographicalZoneDto geographicalZone;

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

    public GeographicalZoneDto getGeographicalZone() {
        return geographicalZone;
    }

    public void setGeographicalZone(GeographicalZoneDto geographicalZone) {
        this.geographicalZone = geographicalZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZooZoneDto zooZone = (ZooZoneDto) o;
        return Objects.equals(name, zooZone.name) &&
                Objects.equals(description, zooZone.description) &&
                Objects.equals(houseCapacity, zooZone.houseCapacity) &&
                Objects.equals(geographicalZone, zooZone.geographicalZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, houseCapacity, geographicalZone);
    }
}
