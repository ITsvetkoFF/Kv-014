package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "geo_zones")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class GeographicalZone extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String regionName;

}
