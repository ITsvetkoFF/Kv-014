package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.model.ZooZone;

import java.util.Map;

/**
 * <p>Specific repository for {@link ZooZone} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
public interface ZooZoneRepository extends Repository<ZooZone> {
    /**
     * @return map containing id of zones as keys and capacity of zone as values
     */
    Map<Long, Long> getCapacityForAllZones();

    Long getCapacityByZoneId(Long id);

    void deleteZoneCapacity(Long zoneId);
}
