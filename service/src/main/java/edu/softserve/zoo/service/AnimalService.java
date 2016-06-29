package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.House;
import edu.softserve.zoo.model.Species;

import java.util.List;

/**
 * Animal specific methods and business logic for service layer
 *
 * @author Serhii Alekseichenko
 */
public interface AnimalService extends Service<Animal> {

    /**
     * Returns the List of {@link Animal} by specified {@link House} id
     *
     * @param houseId of {@link House}
     * @return List of {@link Animal}
     */
    List<Animal> getAllByHouseId(Long houseId);

    /**
     * Returns the List of {@link Animal} by specified {@link Species} id
     *
     * @param speciesId of {@link Species}
     * @return List of {@link Animal}
     */
    List<Animal> getAllBySpeciesId(Long speciesId);

    /**
     * Returns {@link Animal} by id with birthday, {@link House} and {@link Species} fields
     *
     * @param id of {@link Animal}
     * @return List of {@link Animal}
     */
    Animal findOneWithBirthdayHouseAndSpecies(Long id);

}
