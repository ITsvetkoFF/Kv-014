package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "zoo_zones")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class ZooZone extends BaseEntity {

    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Column(name = "description", nullable = false, length = 100)
    private String description;
    @Column(name = "house_capacity", nullable = false)
    private Integer houseCapacity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geo_zone_id", nullable = false)
    private GeographicalZone geographicalZone;

}
