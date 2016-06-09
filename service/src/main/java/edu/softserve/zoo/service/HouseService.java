package edu.softserve.zoo.service;

import edu.softserve.zoo.model.House;

import java.util.List;

/**
 * House specific methods and business logic for service layer
 *
 * @author Serhii Alekseichenko
 */
public interface HouseService extends Service<House> {

    /**
     * Returns the List of {@link House} by specified {@link edu.softserve.zoo.model.ZooZone} id
     *
     * @param id of {@link edu.softserve.zoo.model.ZooZone}
     * @return List of {@link House}
     */
    List<House> getAllByZooZoneId(Long id);

}
