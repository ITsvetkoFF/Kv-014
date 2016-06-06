package edu.softserve.zoo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "geo_zones")
public class GeographicalZone extends BaseEntity {
    private String regionName;

    public GeographicalZone() {
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "GeographicalZone{" +
                "id=" + getId() +
                ", regionName=" + regionName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicalZone that = (GeographicalZone) o;
        return Objects.equals(regionName, that.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionName);
    }
}
