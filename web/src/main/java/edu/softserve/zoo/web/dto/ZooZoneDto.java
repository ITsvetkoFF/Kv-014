package edu.softserve.zoo.web.dto;

import edu.softserve.zoo.core.model.ZooZone;
import edu.softserve.zoo.web.annotation.DocsFieldDescription;
import edu.softserve.zoo.web.annotation.Dto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Dto(ZooZone.class)
public class ZooZoneDto extends BaseDto {

    @DocsFieldDescription("The name")
    @NotNull
    @NotEmpty
    @Length(max = 20)
    private String name;

    @DocsFieldDescription("The description")
    @Length(max = 100)
    @NotNull
    @NotEmpty
    private String description;

    @DocsFieldDescription("The house capacity")
    @NotNull
    @Min(0)
    private Integer houseCapacity;

    @DocsFieldDescription("The geographical zone")
    @NotNull
    private GeographicalZoneDto geographicalZone;

    public ZooZoneDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHouseCapacity() {
        return houseCapacity;
    }

    public void setHouseCapacity(Integer houseCapacity) {
        this.houseCapacity = houseCapacity;
    }

    public GeographicalZoneDto getGeographicalZone() {
        return geographicalZone;
    }

    public void setGeographicalZone(GeographicalZoneDto geographicalZone) {
        this.geographicalZone = geographicalZone;
    }

    @Override
    public String toString() {
        return "ZooZoneDto{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", houseCapacity=" + houseCapacity +
                ", geographicalZone=" + geographicalZone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZooZoneDto zooZoneDto = (ZooZoneDto) o;
        return Objects.equals(name, zooZoneDto.name) &&
                Objects.equals(description, zooZoneDto.description) &&
                Objects.equals(houseCapacity, zooZoneDto.houseCapacity) &&
                Objects.equals(geographicalZone, zooZoneDto.geographicalZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, houseCapacity, geographicalZone);
    }
}
