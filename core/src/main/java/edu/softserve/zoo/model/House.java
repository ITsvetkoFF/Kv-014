package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "houses")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class House extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zoo_zone_id", nullable = false)
    private ZooZone zone;
    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

}
