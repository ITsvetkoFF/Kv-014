package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.persistence.repository.AnimalRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of all {@link AnimalRepository} specific addons for {@link Animal} entity</p>
 *
 * @author Serhii Alekseichenko
 */
@Repository
public class AnimalRepositoryImpl extends AbstractRepository<Animal> implements AnimalRepository {
    @Override
    protected Class<Animal> getEntityType() {
        return Animal.class;
    }
}
