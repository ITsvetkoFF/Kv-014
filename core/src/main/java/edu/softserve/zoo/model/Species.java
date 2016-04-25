package edu.softserve.zoo.model;

import java.util.Objects;
import java.util.Set;

public class Species extends BaseEntity {
    private Family family;
    private String scientificName;
    private String commonName;
    private Set<GeographicalZone> geographicalZones;

    public Species() {
    }

    public Set<GeographicalZone> getGeographicalZones() {
        return geographicalZones;
    }

    public void setGeographicalZones(Set<GeographicalZone> geographicalZones) {
        this.geographicalZones = geographicalZones;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String longName) {
        this.scientificName = longName;
    }

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
