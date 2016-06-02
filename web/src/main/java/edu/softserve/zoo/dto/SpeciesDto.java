package edu.softserve.zoo.dto;

import edu.softserve.zoo.annotation.DocsDescription;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.annotation.Dto;

import java.util.Objects;
import java.util.Set;

@Dto(Species.class)
public class SpeciesDto extends BaseDto {
    @DocsDescription("The family")
    private FamilyDto family;
    @DocsDescription("The scientific name")
    private String scientificName;
    @DocsDescription("The common name")
    private String commonName;
    @DocsDescription("The geographical zones")
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

    @Override
    public String toString() {
        return "SpeciesDto{" +
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
        SpeciesDto speciesDto = (SpeciesDto) o;
        return Objects.equals(family, speciesDto.family) &&
                Objects.equals(scientificName, speciesDto.scientificName) &&
                Objects.equals(commonName, speciesDto.commonName) &&
                Objects.equals(geographicalZones, speciesDto.geographicalZones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(family, scientificName, commonName, geographicalZones);
    }
}
