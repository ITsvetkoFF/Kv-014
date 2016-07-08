package edu.softserve.zoo.service;

import edu.softserve.zoo.model.ZooZone;

/**
 * {@link Service} serves to manage {@link ZooZone} domain objects
 *
 * @author Vadym Holub
 */
public interface ZooZoneService extends Service<ZooZone> {

    Long getZoneCapacityById(Long zoneId);
}
