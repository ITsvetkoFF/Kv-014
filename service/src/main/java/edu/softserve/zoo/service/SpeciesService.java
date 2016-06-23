package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Species;

/**
 * Species specific methods and business logic for service layer
 *
 * @author Serhii Alekseichenko
 */
public interface SpeciesService extends Service<Species> {

    /**
     * Returns {@link Species} by id with animalsPerHouse field
     *
     * @param id of {@link Species}
     * @return {@link Species} with animalsPerHouse field
     */
    Species findOneWithAnimalsPerHouse(Long id);

}
