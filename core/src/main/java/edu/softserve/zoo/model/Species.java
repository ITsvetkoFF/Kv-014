package edu.softserve.zoo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "species")
@EqualsAndHashCode(callSuper = false)
@Access(AccessType.FIELD)
public class Species extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;
    @Column(name = "scientific_name", nullable = false, length = 50)
    private String scientificName;
    @Column(name = "common_name", length = 50)
    private String commonName;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "geo_zone_species_mapping",
            joinColumns = @JoinColumn(name = "species_id"),
            inverseJoinColumns = @JoinColumn(name = "geo_zone_id"))
    private Set<GeographicalZone> geographicalZones;

}
