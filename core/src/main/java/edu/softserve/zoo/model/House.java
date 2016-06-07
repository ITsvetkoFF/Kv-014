package edu.softserve.zoo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "houses")
public class House extends BaseEntity {
    private String name;
    private ZooZone zone;
    private Integer maxCapacity;

    public House() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zoo_zone_id", nullable = false)
    public ZooZone getZone() {
        return zone;
    }

    public void setZone(ZooZone zone) {
        this.zone = zone;
    }

    @Column(name = "max_capacity", nullable = false)
    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return "House{" +
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
        House house = (House) o;
        return Objects.equals(name, house.name) &&
                Objects.equals(zone, house.zone) &&
                Objects.equals(maxCapacity, house.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, zone, maxCapacity);
    }
}
