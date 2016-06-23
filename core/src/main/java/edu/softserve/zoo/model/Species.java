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
    private Integer animalsPerHouse;
    private Integer temperatureMin;
    private Integer temperatureMax;

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

    @Column(name = "animals_per_house", nullable = false)
    public Integer getAnimalsPerHouse() {
        return animalsPerHouse;
    }

    public void setAnimalsPerHouse(Integer animalsPerHouse) {
        this.animalsPerHouse = animalsPerHouse;
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

    @Column(name = "temperature_min", nullable = false)
    public Integer getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Integer temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    @Column(name = "temperature_max", nullable = false)
    public Integer getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Integer temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    @Override
    public String toString() {
        return "Species{" +
                "id=" + getId() +
                ", family=" + family +
                ", scientificName='" + scientificName + '\'' +
                ", commonName='" + commonName + '\'' +
                ", animalsPerHouse=" + animalsPerHouse +
                ", geographicalZones=" + geographicalZones +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
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
                Objects.equals(geographicalZones, species.geographicalZones) &&
                Objects.equals(temperatureMin, species.temperatureMin) &&
                Objects.equals(temperatureMax, species.temperatureMax) &&
                Objects.equals(animalsPerHouse, species.animalsPerHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(family, scientificName, commonName, geographicalZones, temperatureMin, temperatureMax, animalsPerHouse);
    }
}
