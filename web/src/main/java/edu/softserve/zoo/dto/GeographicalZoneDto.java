package edu.softserve.zoo.dto;

import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.annotation.Dto;

import java.util.Objects;

@Dto(GeographicalZone.class)
public class GeographicalZoneDto extends BaseDto {
    private String regionName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicalZoneDto that = (GeographicalZoneDto) o;
        return Objects.equals(regionName, that.regionName);
    }

    @Override
    public String toString() {
        return "GeographicalZoneDto{" +
                "regionName='" + regionName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionName);
    }
}