package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsFieldDescription;
import edu.softserve.zoo.annotation.Dto;
import edu.softserve.zoo.model.Species;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Dto(Species.class)
public class SpeciesDto extends BaseDto {

    @DocsFieldDescription("The family")
    @NotNull
    private FamilyDto family;

    @DocsFieldDescription("The scientific name")
    @NotNull
    @NotEmpty
    @Length(max = 50)
    private String scientificName;

    @DocsFieldDescription("The common name")
    @NotNull
    @NotEmpty
    @Length(max = 50)
    private String commonName;

    @NotNull
    @DocsFieldDescription("The min temperature")
    private Integer temperatureMin;

    @NotNull
    @DocsFieldDescription("The max temperature")
    private Integer temperatureMax;

    @NotNull
    @DocsFieldDescription("The count od animals per house")
    private Integer animalsPerHouse;

    @DocsFieldDescription(value = "The geographical zones", optional = true)
    private Set<GeographicalZoneDto> geographicalZones;

    public SpeciesDto() {
    }

    public Set<GeographicalZoneDto> getGeographicalZones() {
        return geographicalZones;
    }

    public void setGeographicalZones(Set<GeographicalZoneDto> geographicalZones) {
        this.geographicalZones = geographicalZones;
    }

    public FamilyDto getFamily() {
        return family;
    }

    public void setFamily(FamilyDto family) {
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

    public Integer getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Integer temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Integer getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Integer temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Integer getAnimalsPerHouse() {
        return animalsPerHouse;
    }

    public void setAnimalsPerHouse(Integer animalsPerHouse) {
        this.animalsPerHouse = animalsPerHouse;
    }

    @Override
    public String toString() {
        return "SpeciesDto{" +
                "id=" + getId() +
                ", family=" + family +
                ", scientificName='" + scientificName + '\'' +
                ", commonName='" + commonName + '\'' +
                ", animalsPerHouse=" + animalsPerHouse +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                ", geographicalZones=" + geographicalZones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeciesDto that = (SpeciesDto) o;
        return Objects.equals(family, that.family) &&
                Objects.equals(scientificName, that.scientificName) &&
                Objects.equals(commonName, that.commonName) &&
                Objects.equals(animalsPerHouse, that.animalsPerHouse) &&
                Objects.equals(temperatureMin, that.temperatureMin) &&
                Objects.equals(temperatureMax, that.temperatureMax) &&
                Objects.equals(geographicalZones, that.geographicalZones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(family, scientificName, commonName, animalsPerHouse, temperatureMin, temperatureMax, geographicalZones);
    }
}
