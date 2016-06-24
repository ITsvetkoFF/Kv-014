package edu.softserve.zoo.persistence.specification.hibernate.impl.house.composite;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import java.util.Objects;

/**
 * @author Vadym Holub
 */
public class HouseZoneField implements HouseField {

    private final String propertyName = "zone";

    @Override
    public Projection getField() {
        return Projections.property(propertyName);
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseZoneField that = (HouseZoneField) o;
        return Objects.equals(propertyName, that.propertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyName);
    }
}
