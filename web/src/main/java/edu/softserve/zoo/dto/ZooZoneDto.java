package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsDescription;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.annotation.Dto;

import java.util.Objects;

@Dto(ZooZone.class)
public class ZooZoneDto extends BaseDto {
    @DocsDescription("The name")
    private String name;
    @DocsDescription("The description")
    private String description;
    @DocsDescription("The house capacity")
    private Integer houseCapacity;
    @DocsDescription("The geographical zone")
    private GeographicalZoneDto geographicalZone;

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

    public GeographicalZoneDto getGeographicalZone() {
        return geographicalZone;
    }

    public void setGeographicalZone(GeographicalZoneDto geographicalZone) {
        this.geographicalZone = geographicalZone;
    }

    @Override
    public String toString() {
        return "ZooZoneDto{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", houseCapacity=" + houseCapacity +
                ", geographicalZone=" + geographicalZone +
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
                Objects.equals(geographicalZone, zooZoneDto.geographicalZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, houseCapacity, geographicalZone);
    }
}
