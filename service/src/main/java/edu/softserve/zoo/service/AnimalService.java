package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Animal;

import java.util.List;

/**
 * Animal specific methods and business logic for service layer
 *
 * @author Serhii Alekseichenko
 */
public interface AnimalService extends Service<Animal> {

    List<Animal> getAllByHouseId(Long id);

    List<Animal> getAllBySpeciesId(Long id);

}
