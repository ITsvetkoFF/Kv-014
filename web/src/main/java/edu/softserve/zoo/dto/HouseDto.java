package edu.softserve.zoo.dto;

import java.util.Objects;

/**
 * @author Ilya Doroshenko
 */
public class HouseDto extends BaseDto {

    private ZooZoneDto zone;
    private Integer maxCapacity;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseDto house = (HouseDto) o;
        return Objects.equals(zone, house.zone) &&
                Objects.equals(maxCapacity, house.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, maxCapacity);
    }
}
