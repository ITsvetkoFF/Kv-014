package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.core.model.GeographicalZone;
import edu.softserve.zoo.web.annotation.DocsFieldDescription;
import edu.softserve.zoo.web.annotation.Dto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Dto(GeographicalZone.class)
public class GeographicalZoneDto extends BaseDto {

    @DocsFieldDescription("The region name")
    @NotNull
    @NotEmpty
    @Length(max = 50)
    private String regionName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicalZoneDto that = (GeographicalZoneDto) o;
        return Objects.equals(regionName, that.regionName);
    }

    @Override
    public String toString() {
        return "GeographicalZoneDto{" +
                "regionName='" + regionName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionName);
    }
}