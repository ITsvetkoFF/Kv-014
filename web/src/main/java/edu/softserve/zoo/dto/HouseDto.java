package edu.softserve.zoo.dto;

import java.util.Objects;

public class HouseDto extends BaseDto {
    private ZooZoneDto zone;
    private Integer maxCapacity;

    public HouseDto() {
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
                Objects.equals(maxCapacity, houseDto.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, maxCapacity);
    }
}
