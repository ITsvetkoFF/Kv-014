package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

public class GeographicalZoneGetAllSpecification implements HQLSpecification<GeographicalZone> {

    private final static String ZONE_TYPE_NAME = GeographicalZone.class.getSimpleName();

    @Override
    public String query() {
        return "from " + ZONE_TYPE_NAME;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GeographicalZoneGetAllSpecification specification = (GeographicalZoneGetAllSpecification) obj;
        return query().equals(specification.query());
    }
}
