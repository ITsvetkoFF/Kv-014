package edu.softserve.zoo.core.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "zoo_zones")
public class ZooZone extends BaseEntity {

    private String name;
    private String description;
    private Integer houseCapacity;
    private GeographicalZone geographicalZone;

    public ZooZone() {
    }

    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "house_capacity", nullable = false)
    public Integer getHouseCapacity() {
        return houseCapacity;
    }

    public void setHouseCapacity(Integer houseCapacity) {
        this.houseCapacity = houseCapacity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geo_zone_id", nullable = false)
    public GeographicalZone getGeographicalZone() {
        return geographicalZone;
    }

    public void setGeographicalZone(GeographicalZone geographicalZone) {
        this.geographicalZone = geographicalZone;
    }

    @Override
    public String toString() {
        return "ZooZone{" +
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
        ZooZone zooZone = (ZooZone) o;
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
