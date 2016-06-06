package edu.softserve.zoo.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "species")
public class Species extends BaseEntity {
    private Family family;
    private String scientificName;
    private String commonName;
    private Set<GeographicalZone> geographicalZones;

    public Species() {
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "geo_zone_species_mapping",
            joinColumns = @JoinColumn(name = "species_id"),
            inverseJoinColumns = @JoinColumn(name = "geo_zone_id"))
    public Set<GeographicalZone> getGeographicalZones() {
        return geographicalZones;
    }

    public void setGeographicalZones(Set<GeographicalZone> geographicalZones) {
        this.geographicalZones = geographicalZones;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id", nullable = false)
    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    @Column(name = "scientific_name", nullable = false, length = 50)
    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String longName) {
        this.scientificName = longName;
    }

    @Column(name = "common_name", length = 50)
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @Override
    public String toString() {
        return "Species{" +
                "id=" + getId() +
                ", family=" + family +
                ", scientificName='" + scientificName + '\'' +
                ", commonName='" + commonName + '\'' +
                ", geographicalZones=" + geographicalZones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return Objects.equals(family, species.family) &&
                Objects.equals(scientificName, species.scientificName) &&
                Objects.equals(commonName, species.commonName) &&
                Objects.equals(geographicalZones, species.geographicalZones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(family, scientificName, commonName, geographicalZones);
    }
}
