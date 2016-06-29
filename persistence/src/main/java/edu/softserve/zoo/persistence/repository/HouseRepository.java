package edu.softserve.zoo.persistence.repository;

import edu.softserve.zoo.model.House;

import java.util.Map;

/**
 * <p>Specific repository for {@link House} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
public interface HouseRepository extends Repository<House> {

    /**
     * @return house capacity map with house Id and current capacity pairs
     */
    Map<Long, Long> getCapacityMap();

}
