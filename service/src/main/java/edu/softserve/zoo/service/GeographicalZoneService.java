package edu.softserve.zoo.service;

import edu.softserve.zoo.model.GeographicalZone;

import java.util.Collection;

public interface GeographicalZoneService extends Service<GeographicalZone> {
    Collection<GeographicalZone> getAll();
}
